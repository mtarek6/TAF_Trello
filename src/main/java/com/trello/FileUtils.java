package com.trello;

import com.trello.utils.logs.LogsManager;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.copyFile;

public class FileUtils {
    private static final String USER_DIR = System.getProperty("user.dir") + File.separator;
    private FileUtils() {
    }

    //renaming
    public static void renameFile(String oldName, String newName) {
        try {
            var targetFile = new File(oldName);
            String targetDirectory = targetFile.getParentFile().getAbsolutePath();
            File newFile = new File(targetDirectory + File.separator + newName);
            if (!targetFile.getPath().equals(newFile.getPath())) {
                copyFile(targetFile, newFile);
                org.apache.commons.io.FileUtils.deleteQuietly(targetFile);
                LogsManager.info("Target File Path: \"" + oldName + "\", file was renamed to \"" + newName + "\".");
            } else {
                LogsManager.info(("Target File Path: \"" + oldName + "\", already has the desired name \"" + newName + "\"."));
            }
        } catch (IOException e) {
            LogsManager.error(e.getMessage());
        }
    }

    //creating directory
    public static void createDirectory(String path) {
        try
        {
            File file = new File(USER_DIR + path);
            if(!file.exists()) {
                if (file.mkdirs()) {
                    LogsManager.info("Directory created: " + file.getAbsolutePath());
                } else {
                    LogsManager.error("Failed to create directory: " + file.getAbsolutePath());
                }
            }
        }
        catch (Exception e) {
            LogsManager.error("Error creating directory: ",  e.getMessage());
        }
    }
    //cleaning directory
    public static void cleanDirectory(File file) {
        try
        {
            org.apache.commons.io.FileUtils.deleteQuietly(file);
        }
        catch (Exception e) {
            LogsManager.error("Error cleaning directory: ", file.getAbsolutePath(), e.getMessage());
        }
    }

    //force delete file
    public static void forceDeleteFile(File file) {
        try {
            org.apache.commons.io.FileUtils.forceDelete(file);
            LogsManager.info("File deleted: " + file.getAbsolutePath());
        } catch (IOException e) {
            LogsManager.error("Error deleting file: ", file.getAbsolutePath(), e.getMessage());
        }
    }

    //check if the file exists
    public static boolean isFileExists(String fileName) {
        String downloadsPath = System.getProperty("user.dir") + File.separator + "/src/test/resources/downloads/";
        File file = new File(downloadsPath + fileName);
        LogsManager.info("Checking if file exists: " + file.getAbsolutePath() + " - " + file.exists());
        return file.exists();
    }

    // wait for file to be downloaded
    public static boolean isFileExist(String fileName, int numberOfRetries) {
        boolean isFileExist = false;
        int i = 0;

        while (i < numberOfRetries) {
            try {
                String filePath = USER_DIR + "/src/test/resources/downloads/";
                isFileExist = (new File(filePath + fileName)).getAbsoluteFile().exists();
            } catch (Exception e) {
                LogsManager.error(e.getMessage());
            }

            if (!isFileExist) {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    LogsManager.error(e.getMessage());
                }
            }
            i++;
        }
        return isFileExist;
    }
}
