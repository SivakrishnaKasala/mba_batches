package com.siva.cob.mba_billgroup_sync_batch.util;

import java.io.File;

public class FileUtil {

    public static boolean isFilePresent(String directoryPath, String fileName) {

        File folder = new File(directoryPath);
        File[] files = folder.listFiles();

        if (files == null || files.length == 0) {
            return false;   // no files
        }

        for (File file : files) {
            if (file.getName().equals(fileName)) {
                return true;
            }
        }
        return false;
    }
}
