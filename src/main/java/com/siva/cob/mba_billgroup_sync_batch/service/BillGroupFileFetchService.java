package com.siva.cob.mba_billgroup_sync_batch.service;


import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class BillGroupFileFetchService {


    public File[] getFiles(String readyDir) throws Exception {

        Path path = Path.of(readyDir);
        System.out.println(path);

        // Check if directory exists
        if (!Files.exists(path)) {
            throw new Exception("Directory does not exist: " + readyDir);
        }

        File folder = path.toFile();

        File[] files = folder.listFiles(File::isFile); // Only files, ignore folders

        // listFiles() can return null if I/O error or path is not a directory
        if (files == null) {
            throw new Exception("Unable to read directory: " + readyDir);
        }

        // No files found
        if (files.length == 0) {
            throw new Exception("No files present in directory: " + readyDir);
        }

        return files;

    }
}
