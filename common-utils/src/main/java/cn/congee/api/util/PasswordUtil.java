package cn.congee.api.util;

import java.util.Random;

/**
 * 随机生成密码工具类
 *
 * @Author: yang
 * @Date: 2020-12-03 14:38
 */
public class PasswordUtil {

    public final static String[] word = {
              "a", "b", "c", "d", "e", "f", "g",
              "h", "i", "j", "k", "l", "m", "n",
              "o", "p", "q", "r", "s", "t",
              "u", "v", "w", "x", "y", "z",
              "A", "B", "C", "D", "E", "F", "G",
              "H", "I", "J", "K", "L", "M", "N",
              "O", "P", "Q", "R", "S", "T",
              "U", "V", "W", "X", "Y", "Z"
    };

    public final static String[] num = {
            "2", "3", "4", "5", "6", "7", "8", "9"
    };

    public static String randomPassword(){
        StringBuffer stringBuffer = new StringBuffer();
        Random random = new Random(System.currentTimeMillis());
        boolean flag = false;
        //输出几位密码长度 这里是有可能8,9,10位
        int length = random.nextInt(3) + 8;
        for (int i = 0; i < length; i++){
            if(flag){
                stringBuffer.append(num[random.nextInt(num.length)]);
            }else {
                stringBuffer.append(word[random.nextInt(word.length)]);
            }
            flag = !flag;
        }
        return stringBuffer.toString();
    }



}
