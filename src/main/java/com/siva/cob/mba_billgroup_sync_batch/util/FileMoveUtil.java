package com.siva.cob.mba_billgroup_sync_batch.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileMoveUtil {

    private static final Logger LOG = LogManager.getLogger(FileMoveUtil.class);

    public static void moveFile(File sourceFile, String targetDir) throws Exception {

        if (sourceFile == null || !sourceFile.exists()) {
            throw new IllegalArgumentException("Source file does not exist: " + sourceFile);
        }

        Path targetDirectory = Path.of(targetDir);

        // Create target directory if not exists
        if (!Files.exists(targetDirectory)) {
            LOG.warn("Target directory does not exist. Creating: {}", targetDirectory);
            Files.createDirectories(targetDirectory);
        }

        // Resolve final target path
        Path targetFile = targetDirectory.resolve(sourceFile.getName());

        try {
            Files.move(
                    sourceFile.toPath(),
                    targetFile,
                    StandardCopyOption.REPLACE_EXISTING
            );
            LOG.info("File successfully moved from {} to {}", sourceFile.getAbsolutePath(), targetFile);
        } catch (Exception ex) {
            LOG.error("Failed to move file {} to {}. Error: {}",
                    sourceFile.getAbsolutePath(), targetFile, ex.getMessage());
            throw ex;
        }
    }
}
