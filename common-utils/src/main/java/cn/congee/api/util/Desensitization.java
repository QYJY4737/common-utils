package src.main.java.cn.congee.api.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 正则表达式 手机号 身份证号脱敏
 * Created by wgb
 * Date: 2020/12/26
 * Time: 16:11
 **/
public class Desensitization {

    /**
     * 手机号码前三后四脱敏
     *
     * @param mobile
     * @return
     */
    public static String mobileEncrypt(String mobile){
        if(StringUtils.isEmpty(mobile) || (mobile.length() != 11)){
            return mobile;
        }
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 身份证前三后四脱敏
     *
     * @param id
     * @return
     */
    public static String idEncrypt(String id){
        if(StringUtils.isEmpty(id) || (id.length() < 8)){
            return id;
        }
        return id.replaceAll("(?<=\\w{3})\\w(?=\\w{4})", "*");
    }

    public static void main(String[] args) {
        System.out.println(Desensitization.mobileEncrypt("18992390326"));
        System.out.println(Desensitization.idEncrypt("610431199604111539"));
    }

}
