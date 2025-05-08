package com.iflytek.csxyb.servlet;

import com.iflytek.csxyb.entity.Audio;
import com.iflytek.csxyb.entity.User;
import com.iflytek.csxyb.entity.UserType;
import com.iflytek.csxyb.utils.audio.api.CreateFeature;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@WebServlet(name = "AudioUploadApiServlet", value = "/AudioUploadApiServlet")  // 映射到 /api/upload
@MultipartConfig  // 启用多部分表单处理
public class AudioUploadApiServlet extends HttpServlet {
    private static String requestUrl = "https://api.xf-yun.com/v1/private/s782b4996";

    //控制台获取以下信息
    private static String APPID = "01b7d702";
    private static String apiSecret = "ODI3ZjNiZWQyNWUzMjM2NWUxNDllODIw";
    private static String apiKey = "de8ee7262fec1af8c18c587ce1d77e9b";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        User user = (User) req.getSession().getAttribute("loginUser");
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        resp.setContentType("text/plain");  // 设置响应内容类型
        resp.setCharacterEncoding("UTF-8");  // 设置字符编码
        if (user==null) {
            user = new User();
            user.setUserId(1);
            user.setType(UserType.root);
            user.setUserName(".thy");
        }

        try {
            // 获取上传的文件部分
            Part filePart = req.getPart("file");  // "file" 是表单字段名
            System.out.println("文件上传中" + filePart);

            // 检查文件是否为空
            if (filePart == null || filePart.getSize() == 0) {
                resp.getWriter().write("文件为空，请选择一个有效的音频文件");
                return;
            }

            // 检查文件类型是否为音频文件
            String contentType = filePart.getContentType();
            if (!contentType.startsWith("audio/")) {
                resp.getWriter().write("只允许上传音频文件");
                return;
            }

            // 定义文件存储路径
            String uploadDir = "D:\\uploadAudio";
            File uploadPath = new File(uploadDir);

            // 如果目录不存在，则创建目录
            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }

            // 构造文件保存路径
//            String originalFileName = getFileNameFromPart(filePart);  // 提取文件名
            String tempFilePath = uploadDir + File.separator + "temp_" + user.getUserId() + ".mp3";
            String convertedFilePath = uploadDir + File.separator + "audioKey_" + user.getUserId() + ".mp3";

            File tempFile = new File(tempFilePath);

            // 将上传的文件保存到临时路径
            filePart.write(tempFile.getAbsolutePath());  // 保存文件

            // 调用转换方法
            Audio.mp3Converter(tempFile.getAbsolutePath(), convertedFilePath);
            CreateFeature.doCreateFeature(requestUrl,APPID,apiSecret,apiKey,convertedFilePath,user);
            // 删除临时文件
            tempFile.delete();
            filePart.delete();

            // 返回成功响应
            resp.getWriter().write("音频文件已成功上传并转换为：" + convertedFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            resp.getWriter().write("文件上传失败：" + e.getMessage());
        }
    }

    // 辅助方法：从 Part 对象中提取文件名
    private String getFileNameFromPart(Part part) {
        String header = part.getHeader("content-disposition");
        String[] items = header.split(";");
        for (String item : items) {
            if (item.trim().startsWith("filename")) {
                return item.substring(item.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return "";  // 如果未找到，返回空字符串
    }
}