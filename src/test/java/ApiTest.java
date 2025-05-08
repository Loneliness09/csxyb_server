import com.iflytek.csxyb.utils.audio.api.*;
import org.junit.Test;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class ApiTest {

    private static String requestUrl = "https://api.xf-yun.com/v1/private/s782b4996";

    //控制台获取以下信息
    private static String APPID = "01b7d702";
    private static String apiSecret = "ODI3ZjNiZWQyNWUzMjM2NWUxNDllODIw";
    private static String apiKey = "de8ee7262fec1af8c18c587ce1d77e9b";

    //音频存放位置
    private static String AUDIO_PATH;
    @Test
    public void test() throws UnsupportedEncodingException {
        AUDIO_PATH = Upgoin.getLatestFilePath("audioExample");
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        System.out.println("你好");
        System.out.println(AUDIO_PATH);

        /**1.创建声纹特征库*/
        CreateGroup.doCreateGroup(requestUrl,APPID,apiSecret,apiKey, "aaa", "aaa");

        /**2.添加音频特征*/
//        CreateFeature.doCreateFeature(requestUrl,APPID,apiSecret,apiKey,AUDIO_PATH);
        /**3.查询特征列表*/
        QueryFeatureList.doQueryFeatureList(requestUrl,APPID,apiSecret,apiKey);
        /**4.特征比对1:1*/
        SearchOneFeature.doSearchOneFeature(requestUrl,APPID,apiSecret,apiKey,AUDIO_PATH);
        /**5.特征比对1:N*/
        SearchFeature.doSearchFeature(requestUrl,APPID,apiSecret,apiKey,AUDIO_PATH);
        /**6.更新音频特征*/
        UpdateFeature.doUpdateFeature(requestUrl,APPID,apiSecret,apiKey,AUDIO_PATH);
        /**7.删除指定特征*/
        DeleteFeature.doDeleteFeature(requestUrl,APPID,apiSecret,apiKey);
        /**8.删除声纹特征库*/
        DeleteGroup.doDeleteGroup(requestUrl,APPID,apiSecret,apiKey);
    }

    @Test
    public void createGroup() {
        CreateGroup.doCreateGroup(requestUrl,APPID,apiSecret,apiKey, "admin", "admin");
        CreateGroup.doCreateGroup(requestUrl,APPID,apiSecret,apiKey, "regular", "regular");
        CreateGroup.doCreateGroup(requestUrl,APPID,apiSecret,apiKey, "root", "root");

    }
    @Test
    public void createGroup1() {
        QueryFeatureList.doQueryFeatureList(requestUrl,APPID,apiSecret,apiKey, "root");

    }
}
