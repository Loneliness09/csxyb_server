package com.iflytek.csxyb.utils.audio.api;

import java.io.File;

public class Upgoin{
    private static String AUDIO_PATH;

    public static void main(String[] args) {
        // 设置文件夹路径
        String folderPath = "audioExample";

        // 获取最新文件的路径
        String latestFilePath = getLatestFilePath(folderPath);

        // 如果找到最新文件，更新 AUDIO_PATH
        if (latestFilePath != null) {
            AUDIO_PATH = latestFilePath;
            System.out.println("最新文件路径: " + AUDIO_PATH);
        } else {
            System.out.println("文件夹为空或不存在");
        }
    }

    // 获取文件夹中最新文件的路径
    public static String getLatestFilePath(String folderPath) {
        // 创建文件对象，表示文件夹
        File folder = new File(folderPath);

        // 检查文件夹是否存在
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("文件夹不存在或路径错误");
            return null;
        }

        // 获取文件夹中的所有文件
        File[] files = folder.listFiles();

        // 如果文件夹为空，返回 null
        if (files == null || files.length == 0) {
            return null;
        }

        // 初始化变量，用于存储最新的文件
        File latestFile = null;
        long latestTime = Long.MIN_VALUE;

        // 遍历文件夹中的所有文件
        for (File file : files) {
            // 如果是文件且最后修改时间大于当前记录的最新时间
            if (file.isFile() && file.lastModified() > latestTime) {
                latestFile = file;
                latestTime = file.lastModified();
            }
        }

        // 返回最新文件的路径
        return latestFile != null ? latestFile.getAbsolutePath() : null;
    }
}