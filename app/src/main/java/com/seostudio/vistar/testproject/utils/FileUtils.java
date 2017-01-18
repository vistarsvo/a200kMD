package com.seostudio.vistar.testproject.utils;

import android.os.Build;
import android.os.StatFs;

import java.io.File;
import java.io.IOException;

public class FileUtils {

    public static boolean checkFileExists(String Path) {
        File dbFile = new File(Path);
        return dbFile.exists();
    }

    public static boolean deleteDataBase(String Path) throws IOException {
        File file = new File(Path);
        return file.delete();
    }

    public static long getAvailableSpaceForData(String path) {
        long availableSpace = -1L;
        try {
            StatFs stat = new StatFs(path);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                availableSpace = stat.getAvailableBlocksLong() * stat.getBlockSizeLong();
            } else {
                availableSpace = (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return availableSpace;
    }

    public static long getAvailableSpaceForDataInMB(String path) {
        long availableSpace = FileUtils.getAvailableSpaceForData(path);
        return Math.round(availableSpace / 1024 / 1024);
    }

}
