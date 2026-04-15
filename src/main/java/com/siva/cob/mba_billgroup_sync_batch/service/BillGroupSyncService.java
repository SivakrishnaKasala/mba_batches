package com.siva.cob.mba_billgroup_sync_batch.service;


import com.siva.cob.mba_billgroup_sync_batch.dao.SupportObjectDAO;
import com.siva.cob.mba_billgroup_sync_batch.dto.SupportObjectDTO;
import com.siva.cob.mba_billgroup_sync_batch.util.BatchExecution;
import com.siva.cob.mba_billgroup_sync_batch.util.FileMoveUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

@Service
public class BillGroupSyncService {

    public static final Logger LOG = LogManager.getLogger(BillGroupSyncService.class);


    @Value("${READY_DIR}")
    public String readyDir;

    @Value("${DONE_DIR}")
    public String doneDir;

    @Value("${WORK_DIR}")
    public String workDir;

    @Value("${ERROR_DIR}")
    public String errorDir;

    @Autowired
    public BillGroupFileFetchService billGroupFileFetchService;

    @Autowired
    public SupportObjectDAO supportObjectDAO;


    public void doSync() throws Exception, FileNotFoundException {

        final List<String> expectedHeader = Arrays.asList("Customer", "AgreeNr", "BillingAccountIdentifier", "BillingAccountIdentifierContext", "BillGroup");

        final String expectedFileNamePattern = "Update_BillGroup_COB";

        BatchExecution execution = new BatchExecution();
        execution.setBatchName("mba_update_billgroup_sync_batch");
        execution.setMode("Update");

        LOG.info("READY_DIR  = {}", readyDir);
        LOG.info("WORK_DIR   = {}", workDir);
        LOG.info("ERROR_DIR  = {}", errorDir);
        LOG.info("DONE_DIR   = {}", doneDir);

        File[] readyFiles = billGroupFileFetchService.getFiles(readyDir);

        System.out.println("hello siva,third commit to repository");

        if (readyFiles == null || readyFiles.length == 0) {
            LOG.error("No input files found in READY directory: {}", readyDir);
            throw new IllegalStateException("No files to process in READY directory");
        }

        for (File inputFile : readyFiles) {

            // Validate filename pattern
            if (!inputFile.getName().contains(expectedFileNamePattern)) {
                LOG.warn("Ignoring file. Name does not match expected pattern: {}", inputFile.getName());
            }else {
                processFile(inputFile, expectedHeader, expectedFileNamePattern, execution);
            }


        }

        LOG.info("Batch Completed: {}", execution);
    }


    private void processFile(File inputFile,
                             List<String> expectedHeader,
                             String expectedFileNamePattern,
                             BatchExecution execution) throws Exception, FileNotFoundException {

        LOG.info("Found file: {}", inputFile.getName());

        String workPath = workDir + File.separator + inputFile.getName();
        String errorPath = errorDir + File.separator + inputFile.getName();
        String donePath = doneDir + File.separator + inputFile.getName();


        File workFile=moveFileToWorkDirectory(inputFile,workDir);


        // Read file
        List<String> lines = Files.readAllLines(workFile.toPath());

        if (lines.isEmpty()) {
            LOG.error("Empty file detected: {}", workFile.getName());
            return;
        }

        validateFile(lines,expectedHeader);

        // Process rows
        processData(lines, execution, expectedHeader);

        if (execution.getSuccessRecord()>1){
            FileMoveUtil.moveFile(inputFile,doneDir);
        }else {
            FileMoveUtil.moveFile(inputFile,errorDir);
        }

        LOG.info("File processed successfully: {}", workFile.getName());
    }

    private File moveFileToWorkDirectory(File inputFile, String workDir) throws IOException {

        // Validate inputs
        if (inputFile == null) {
            throw new IllegalArgumentException("Input file is null");
        }
        if (!inputFile.exists() || !inputFile.isFile()) {
            throw new FileNotFoundException("Input file does not exist: " + inputFile.getAbsolutePath());
        }
        if (workDir == null || workDir.isEmpty()) {
            throw new IllegalArgumentException("Work directory path is empty");
        }

        LOG.info("Starting file move: {} → {}", inputFile.getAbsolutePath(), workDir);

        // Try to move the file
        try {
            FileMoveUtil.moveFile(inputFile, workDir);
        } catch (Exception ex) {
            LOG.error("Failed to move file {} → WORK. Reason: {}",
                    inputFile.getName(), ex.getMessage(), ex);
            throw new IOException("Unable to move file: " + inputFile.getAbsolutePath(), ex);
        }

        // Build path of file in WORK directory
        File workFile = new File(workDir, inputFile.getName());

        // Validate move was successful
        if (!workFile.exists()) {
            LOG.error("File not found after moving to WORK: {}", workFile.getAbsolutePath());
            throw new FileNotFoundException("Move failed: " + workFile.getAbsolutePath());
        }

        LOG.info("File successfully moved to WORK: {}", workFile.getAbsolutePath());
        return workFile;
    }


    private void validateFile(List<String> lines,List<String> expectedHeader) throws Exception {
        // Validate header
        validateHeader(lines.get(0), expectedHeader);
        // Validate footer
        validateFooter(lines.get(lines.size() - 1), lines.size() - 2);
    }

    private BatchExecution processData(List<String> lines,
                                       BatchExecution execution,
                                       List<String> expectedHeader) {

        int recordCount = lines.size() - 2;
        execution.setTotalRecord(recordCount);

        for (int i = 1; i < lines.size() - 1; i++) {

            List<String> row = Arrays.asList(lines.get(i).split("\\|#\\|"));

            if (row.size() != expectedHeader.size()) {
                execution.incrementTotalFailed();
                continue;
            }

            try {
                if (isSuccessRecord(row)) {
                    handleSuccessRecord(row, execution);
                } else if (isFailureRecord(row)) {
                    execution.incrementTotalFailed();
                } else if (isSkippedRecord(row)) {
                    execution.incrementTotalSkipped();
                } else {
                    execution.incrementTotalFailed();
                }
            } catch (Exception e) {
                LOG.error("Error processing row: {}", row, e);
                execution.incrementTotalFailed();
            }
        }

        return execution;
    }

    private void handleSuccessRecord(List<String> row, BatchExecution execution) {
        if (!isValidFormat(row) || !validateContext(row.get(3))) {
            execution.incrementTotalFailed();
            return;
        }

        SupportObjectDTO supportObject=supportObjectDAO.findSupportObject();
        String identifier=supportObject.getIdentifier();

        Integer increment=Integer.valueOf(identifier.substring(3))+1;
        System.out.println(increment);

        String id="SO-"+increment;
        System.out.println(id);
        SupportObjectDTO dto = mapSupportObject(row,id);

        LOG.info("Data received from COB:[{}]",row);
        LOG.info("SupportObject created :{}",dto);

        int result = supportObjectDAO.insertSupportObject(dto);

        // 4. Update execution counters
        if (result == 1) {
            execution.incrementTotalSuccess();
            LOG.info("Inserted SupportObject successfully: {}", dto.getIdentifier());
        } else {
            execution.incrementTotalFailed();
            LOG.error("Failed to insert SupportObject: {}", dto.getIdentifier());
        }
    }


    private SupportObjectDTO mapSupportObject(List<String> row,String id) {

        SupportObjectDTO supportObject = new SupportObjectDTO();

        supportObject.setIdentifier(id);
        supportObject.setCustomerId(row.get(0));
        supportObject.setPa(row.get(1));
        supportObject.setBillingAccountIdentifier(row.get(2));
        supportObject.setBillingAccountContext(row.get(3));
        supportObject.setBillGroup(row.get(4));

        return supportObject;
    }

    private void validateHeader(String headerLine, List<String> expected) throws Exception {

        LOG.info("Validating header...");

        List<String> actual = Arrays.asList(headerLine.split("\\|#\\|"));

        if (actual.size() != expected.size()) {
            throw new Exception("Header mismatch → Expected " + expected.size() +
                    " columns, Found: " + actual.size());
        }

        for (int i = 0; i < expected.size(); i++) {
            if (!actual.get(i).trim().equals(expected.get(i).trim())) {
                throw new Exception("Header mismatch at column " + (i + 1) +
                        " → expected '" + expected.get(i) +
                        "' but found '" + actual.get(i) + "'");
            }
        }

        LOG.info("✔ Header validation successful");
    }


    private void validateFooter(String footer, int expectedCount) throws Exception {

        LOG.info("Validating footer: {}", footer);

        List<String> parts = Arrays.asList(footer.split("\\|#\\|"));

        if (parts.size() != 3 && parts.size() < 3) {
            throw new Exception("Footer must contain 3 fields. Found: " + parts.size());
        }

        if (!"EOF".equals(parts.get(0))) {
            throw new Exception("Footer must start with 'EOF'. Found: " + parts.get(0));
        }

        if (!parts.get(1).equals(String.valueOf(expectedCount))) {
            throw new Exception("Footer count mismatch. Expected: " + expectedCount +
                    ", Found: " + parts.get(1));
        }

        if (!isValidId(parts.get(2))) {
            throw new Exception("Invalid Footer ID: " + parts.get(2));
        }

        LOG.info("✔ Footer validation successful");
    }


    private boolean validateContext(String context) {
        return "MBA".equals(context) && context.length() == 3;
    }

    private boolean isValidId(String id) {
        return id.matches("^[A-Za-z]{2}\\d+$");
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    private boolean isSkippedRecord(List<String> row) {
        return isNotEmpty(row.get(0)) &&
                isNotEmpty(row.get(1)) &&
                isEmpty(row.get(2)) &&
                isNotEmpty(row.get(3)) &&
                isNotEmpty(row.get(4));
    }

    private boolean isFailureRecord(List<String> row) {
        return isEmpty(row.get(0)) ||
                isEmpty(row.get(1)) ||
                isEmpty(row.get(3)) ||
                isEmpty(row.get(4));
    }

    private boolean isSuccessRecord(List<String> row) {
        return isNotEmpty(row.get(0)) &&
                isNotEmpty(row.get(1)) &&
                isNotEmpty(row.get(2)) &&
                isNotEmpty(row.get(3)) &&
                isNotEmpty(row.get(4));
    }

    private boolean isValidFormat(List<String> row) {

        for (int i = 0; i < row.size(); i++) {
            switch (i) {
                case 0, 1, 2 -> {  // numeric fields
                    if (!row.get(i).matches("\\d+")) return false;
                }
                case 3, 4 -> {     // string fields
                    if (isEmpty(row.get(i))) return false;
                }
                default -> {
                    return false;
                }
            }
        }

        return true;
    }


}
