package cn.congee.api.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
/**
 * @Author: yang
 * @Date: 2020-12-04 8:15
 */
public class HttpWebServiceUtil {

    /**
     * http远程访问SOAP协议接口
     *
     * @param url 服务接口地址http://www.webxml.com.cn/WebServices/WeatherWebService.asmx?wsdl
     * @param isClass 接口类名
     * @param isMethod 接口方法名
     * @param paramsXml soap协议xml格式访问接口
     * @param token
     * @return soap协议xml格式
     * 备注:有四种请求头格式1、SOAP 1.1 2、SOAP 1.2 3、HTTP GET 4、HTTP POST
     */
    public static String sendWebService(String url, String isClass, String isMethod, StringBuffer paramsXml, String token){
        try {
            //传过来的xml格式
            String xml = paramsXml.toString();
            if (xml == null){
                return "传入xml参数不能为空!";
            }
            URL sopul = new URL(url);
            //获取连接-11:30
            URLConnection connection = sopul.openConnection();
            System.setProperty("sun.net.client.defaultConnectionTimeout", "30000");
            System.setProperty("sun.net.client.defaultReadTimeout", "30000");

            //缓存设置(POST提交不能有缓存)
            connection.setUseCaches(false);
            //设置输入输出,新创建的connection默认是没有读写权限的
            connection.setDoInput(true);
            connection.setDoOutput(true);
            //设置请求头
            connection.setRequestProperty("Content-Length", xml.length() + "");
            //这里设置请求头类型为xml,传统http请求的是超文本传输格式text/html
            connection.setRequestProperty("Content-Type", "text/xml;charset=uft-8");

            //调用的类名,方法名
            if(isMethod != null){
                connection.setRequestProperty(isClass, isMethod);
            }
            //这里如果携带token设置token,没传则不设置
            if(token != null){
                connection.setRequestProperty("Authorization", "Bearer " + token);
            }
            //获得输出流
            OutputStream os = connection.getOutputStream();
            //发送数据 这里注意要带上编码utf-8 不然 不能传递中文参数过去
            OutputStreamWriter osw = new OutputStreamWriter(os, "utf-8");
            //PrintWriter osw = new PrintWriter(os);
            //写入传过来的xml文件格式
            osw.write(xml);
            //释放资源
            osw.flush();
            osw.close();
            //下面获取webservice返回过来的流
            InputStream is = connection.getInputStream();
            if(is != null){
                byte[] bytes = new byte[0];
                bytes = new byte[is.available()];
                is.read(bytes);
                //将字节数组转换为字符串输出
                String outXml = new String(bytes, "UTF-8");
                return outXml;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
