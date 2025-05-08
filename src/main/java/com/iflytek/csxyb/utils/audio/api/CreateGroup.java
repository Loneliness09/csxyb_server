package com.iflytek.csxyb.utils.audio.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 *创建声纹特征库
 */
public class CreateGroup {
    private String requestUrl;
    private String APPID;
    private String apiSecret;
    private String apiKey;
    private String groupId;
    private String groupName;
    private String groupInfo = "CSXYB";

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    //解析Json
    private static Gson json = new Gson();
    //构造函数,为成员变量赋值
    public CreateGroup(String requestUrl,String APPID,String apiSecret,String apiKey){
        this.requestUrl=requestUrl;
        this.APPID=APPID;
        this.apiSecret=apiSecret;
        this.apiKey=apiKey;
    }

    //提供给主函数调用的方法
    public static void doCreateGroup(String requestUrl,String APPID,String apiSecret,String apiKey, String groupId, String groupName){
        CreateGroup createGroup = new CreateGroup(requestUrl,APPID,apiSecret,apiKey);
        createGroup.setGroupId(groupId);
        createGroup.setGroupName(groupName);
        try {
            String resp = createGroup.doRequest();
            JsonParse myJsonParse = json.fromJson(resp, JsonParse.class);
            String textBase64Decode=new String(Base64.getDecoder().decode(myJsonParse.payload.createGroupRes.text), "UTF-8");
            JSONObject jsonObject = JSON.parseObject(textBase64Decode);
            System.out.println("创建声纹特征库"+jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 请求主方法
     * @return 返回服务结果
     * @throws Exception 异常
     */
    public String doRequest() throws Exception {
        URL realUrl = new URL(buildRequetUrl());
        URLConnection connection = realUrl.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-type","application/json");

        OutputStream out = httpURLConnection.getOutputStream();
        String params = buildParam();
        out.write(params.getBytes());
        out.flush();
        InputStream is = null;
        try{
            is = httpURLConnection.getInputStream();
        }catch (Exception e){
            is = httpURLConnection.getErrorStream();
            throw new Exception("make request error:"+"code is "+httpURLConnection.getResponseMessage()+readAllBytes(is));
        }
        return readAllBytes(is);
    }

    /**
     * 处理请求URL
     * 封装鉴权参数等
     * @return 处理后的URL
     */
    public String buildRequetUrl() {
        URL url = null;
        String httpRequestUrl = requestUrl.replace("ws://", "http://").replace("wss://", "https://");
        try {
            url = new URL(httpRequestUrl);
            // 获取当前日期并格式化
            SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            String date = format.format(new Date());
            String host = url.getHost();


            StringBuilder builder = new StringBuilder("host: ").append(host).append("\n").//
                    append("date: ").append(date).append("\n").//
                    append("POST ").append(url.getPath()).append(" HTTP/1.1");
            System.err.println(builder);

            Charset charset = Charset.forName("UTF-8");
            Mac mac = Mac.getInstance("hmacsha256");
            SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(charset), "hmacsha256");
            mac.init(spec);
            byte[] hexDigits = mac.doFinal(builder.toString().getBytes(charset));
            String sha = Base64.getEncoder().encodeToString(hexDigits);


            String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line", sha);
            String authBase = Base64.getEncoder().encodeToString(authorization.getBytes(charset));


            // 解码 sha 和 authBase
            String authBaseDecoded = new String(Base64.getDecoder().decode(authBase), charset);

            // 打印解码后的结果
            System.out.println("解码后的 authBase: " + authBaseDecoded);
            // 提取并打印 signature
            String signature = authBaseDecoded.split(", ")[3].split("=")[1].trim();
            System.out.println("提取Signature: " + signature);
            return String.format("%s?authorization=%s&host=%s&date=%s", requestUrl, URLEncoder.encode(authBase), URLEncoder.encode(host), URLEncoder.encode(date));
        } catch (Exception e) {
            throw new RuntimeException("assemble requestUrl error:" + e.getMessage());
        }
    }

    /**
     * 组装请求参数
     * 直接使用示例参数，
     * 替换部分值
     * @return 参数字符串
     */
    private String  buildParam() {
        String param = "{"+
                "    \"header\": {"+
                "        \"app_id\": \""+APPID+"\","+
                "        \"status\": 3"+
                "    },"+
                "    \"parameter\": {"+
                "        \"s782b4996\": {"+
                "            \"func\": \"createGroup\","+
                //这里填上所需要的groupId
                "            \"groupId\": \"" + groupId + "\","+
                //这里填上所需要的groupName
                "            \"groupName\": \"" + groupName + "\","+
                //这里填上所需要的groupInfo
                "            \"groupInfo\": \"" + groupInfo + "\","+
                "            \"createGroupRes\": {"+
                "                \"encoding\": \"utf8\","+
                "                \"compress\": \"raw\","+
                "                \"format\": \"json\""+
                "            }"+
                "        }"+
                "    }"+
                "}";
        return param;
    }

    /**
     * 读取流数据
     * @param is 流
     * @return 字符串
     * @throws IOException 异常
     */
    private String readAllBytes(InputStream is) throws IOException {
        byte[] b = new byte[1024];
        StringBuilder sb = new StringBuilder();
        int len = 0;
        while ((len = is.read(b)) != -1){
            sb.append(new String(b, 0, len, "utf-8"));
        }
        return sb.toString();
    }
    //Json解析
    class JsonParse {
        public Header header;
        public Payload payload;
    }
    class Header{
        public int code;
        public String message;
        public String sid;
        public int status;
    }
    class Payload{
        //根据model的取值不同,名字有所变动。
        public CreateGroupRes createGroupRes;
    }
    class CreateGroupRes{
        public String compress;
        public String encoding;
        public String format;
        public String text;
    }
}