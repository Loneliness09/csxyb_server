package com.iflytek.csxyb.entity;

import com.iflytek.csxyb.utils.audio.api.CreateFeature;

import java.io.*;
import java.net.URL;

public class Audio {

    public static void mp3Converter(String inputPath, String outputPath) {

        String[] command = {
                "ffmpeg",
                "-y",  // 自动覆盖输出文件
                "-i", inputPath,  // 输入文件
                "-acodec", "mp3",  // 使用 MP3 编码器
                "-ac", "1",  // 单声道
                "-ar", "16000",  // 采样率为 16000 Hz
                "-b:a", "128k",  // 设置音频比特率为 128kbps
                outputPath  // 输出文件
        };
        try {
            // 创建 ProcessBuilder
            ProcessBuilder processBuilder = new ProcessBuilder(command);

            // 合并错误流和标准输出流
            processBuilder.redirectErrorStream(true);

            // 启动进程
            Process process = processBuilder.start();

            // 读取输出
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // 等待进程完成
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("转换成功！");

            } else {
                System.out.println("转换失败！退出码: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("转换过程中出现错误: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        try {
            mp3Converter("C:\\Users\\Jarvis Sun\\Desktop\\work\\25课程\\后端\\第二阶段\\csxyb_server\\src\\main\\resources\\audio\\mp3_1746587071790.mp3", "C:\\Users\\Jarvis Sun\\Desktop\\work\\25课程\\后端\\第二阶段\\csxyb_server\\src\\main\\resources\\audio\\mp3_1746587071790-t.mp3");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
