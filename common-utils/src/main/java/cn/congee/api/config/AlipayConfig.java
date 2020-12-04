package cn.congee.api.config;

import java.io.FileWriter;
import java.io.IOException;
/**
 * @Author: yang
 * @Date: 2020-12-04 8:25
 */
public class AlipayConfig {

    //=====================配置基本信息===================
    //应用ID,您的APPID,收款账号既是您的APPID对应支付宝账号
    public static String APPID = "2016101200671768";

    //商户私钥,您的PKCS8格式RSA2私钥
    public static String MERCHANT_PRIVATE_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAr3rIhRtgFdqehqazHYS1R/kwOXrzxKbHSiDZm5d68u1akUWQGeGKw7qS7Qror4cH+DlVYeSeNpdlUWoJZ5po4n0vTG27c/I0pR57nfkj4Bn/OTRdRIEGhbOQ21JIeWwghllyu/cdOVJPstChwnQw0fVCcBaToWxBSHevX5HdYGTDGH8Js4R8uaB4aPXXJVdfbGn1KMROcQeRPkBxN4Dvulw8zHazri0iiMSu2jtUGFY3621yvsk0Zqa37zGnhBQgfHuSFoiQeFeh4fE0Cx7oEAtz0Z1IaAxSRRZZF1q5vQcpIxotmgrfzmZ8P6zjCamVd8sEv4R8fZnahg10iHcsjwIDAQAB";

    //支付宝公钥,查看地址:https://openhome.alipay.com/platform/keyManage.html 对应APPID下的支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA3NlIrHVqaKxsnX0xbkylzl1iPcckd9stfoR1BGxbk6r2jdfSZaekYLZwy2SaKlOqbPY/eIGvULQaVF6ovhl/9FNJ7soHXGbhZjZaAFt9rsOF/JD8LFo3k0YBZZ6c3OPfdSZj+LG8JdyROHCJkhTBm0eN8R5bb+cXf9rFWwIMh1t++LxmNq56D9Oy8kl+YBfWNoaRnQhfbJ57lJd+8M3gBe/5oqzpt/DKlGnRbmrQogRU6baiV1FoVvZhNtbzTpOcguSXZXZzA/NZX+zgV2a1c2W78Bh0jbSWzwgMdkipOuUD1jwmfgwLvtzZH07/C60GB2xZl7DSMoQ5kXubMT+HIQIDAQAB";

    //服务器异步通知页面路径,需http://格式的完整路径,不能加?id=123这类自定义参数,必须外网可以正常访问
    public static String NOTIFY_URL = "http://www.baidu.com";

    //页面跳转同步通知页面路径,需http://格式的完整路径,不能加?id=123这类自定义参数,必须外网可以正常访问
    public static String RETURN_URL = "http://www.baidu.com";

    //签名方式
    public static String SIGN_TYPE = "RSA2";

    //字符编码格式
    public static String CHARSET = "utf-8";

    //支付宝网关
    public static String GATEWAYURL = "https://openapi.alipaydev.com/gateway.do";

    //支付宝网关
    public static String LOG_PATH = "C:\\";

    /**
     * 写日志,方便测试(也可以改成把记录存入数据库)
     *
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord){
        FileWriter writer = null;
        try {
            writer = new FileWriter(LOG_PATH + "alipay_log_" + System.currentTimeMillis() + ".txt");
            writer.write(sWord);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(writer != null){
                try {
                    writer.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

}
