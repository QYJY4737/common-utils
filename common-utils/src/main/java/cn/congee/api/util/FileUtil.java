package cn.congee.api.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

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

    /**
     * MultipartFile->File
     *
     * @param file
     * @return
     */
    public static File multipartFileToFile(MultipartFile file){
        File toFile = null;
        try {
            if(file.equals("") || file.getSize() <= 0){
                file = null;
            }else {
                InputStream ins = null;
                ins = file.getInputStream();
                toFile = new File(file.getOriginalFilename());
                inputStreamToFile(ins, toFile);
                ins.close();
            }
        }catch (Exception e){
            log.error("MultipartFile->File报错: " + e.getMessage());
            e.printStackTrace();
        }finally {
            return toFile;
        }
    }

    /**
     * 获取流文件
     *
     * @param ins
     * @param file
     */
    public static void inputStreamToFile(InputStream ins, File file){
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1){
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        }catch (Exception e){
            log.error("获取流文件报错: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 删除本地临时文件
     *
     * @param file
     */
    public static void deleteTempFile(File file){
        if(file != null){
            File del = new File(file.toURI());
            del.delete();
        }
    }

    /**
     * String->InputStream
     *
     * @param str
     * @return
     */
    public static InputStream str2InputStream(String str){
        ByteArrayInputStream is = new ByteArrayInputStream(str.getBytes());
        return is;
    }

    /**
     * InputStream->String
     *
     * @param is
     * @return
     */
    public static String inputStream2Str(InputStream is){
        StringBuffer sb = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            sb = new StringBuffer();
            String data;
            while ((data = br.readLine()) != null){
                sb.append(data);
            }
            br.close();
        }catch (IOException e){
            log.error("InputStream->String报错: " + e.getMessage());
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * File->InputStream
     *
     * @param file
     * @return
     */
    public static InputStream file2InputStream(File file){
        try {
            return new FileInputStream(file);
        }catch (FileNotFoundException fe){
            log.error("File->InputStream报错: " + fe.getMessage());
            fe.printStackTrace();
        }
        return null;
    }

    /**
     * File->InputStream
     *
     * @param is
     * @param file
     */
    public static void inputStream2File(InputStream is, File file){
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int len = 0;
            byte[] buffer = new byte[8192];
            while ((len = is.read(buffer)) != -1){
                os.write(buffer, 0, len);
            }
            os.close();
            is.close();
        }catch (IOException ie){
            log.error(": " + ie.getMessage());
            ie.printStackTrace();
        }
    }

}
