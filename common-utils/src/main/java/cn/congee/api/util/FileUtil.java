package cn.congee.api.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * 文件流工具类
 *
 * @Author: yang
 * @Date: 2020-12-03 18:50
 */
@Slf4j
public class FileUtil {

    /**
     * 读取txt文件的内容
     *
     * @param file
     * @return
     */
    public static String txt2String(File file){
        StringBuilder result = new StringBuilder();
        try{
            //构造一个BufferedReader类来读取文件
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = null;
            while ((s = br.readLine()) != null){
                //使用readLine方法,一次读一行
                result.append(System.lineSeparator() + s);
            }
            br.close();
        }catch (Exception e){
            log.error("读取失败: " + e.getMessage());
            e.printStackTrace();
        }
        return result.toString();
    }

}
