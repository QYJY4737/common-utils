package cn.congee.api.util;

/**
 * @Author: yang
 * @Date: 2020-12-04 8:22
 */
public class HttpWebServiceUtilTest {

    public static void main(String[] args) {
        //调用方法如下：输入参数：theCityName = 城市中文名称(国外城市可用英文)或城市代码(不输入默认为上海市)，如：上海 或 58367，
        // 如有城市名称重复请使用城市代码查询(可通过 getSupportCity 或 getSupportDataSet 获得)；返回数据： 一个一维数组 String(22)，共有23个元素。
        //String(0) 到 String(4)：省份，城市，城市代码，城市图片名称，最后更新时间。String(5) 到 String(11)：当天的 气温，概况，
        // 风向和风力，天气趋势开始图片名称(以下称：图标一)，天气趋势结束图片名称(以下称：图标二)，现在的天气实况，天气和生活指数。
        // String(12) 到 String(16)：第二天的 气温，概况，风向和风力，图标一，图标二。String(17) 到 String(21)：第三天的 气温，概况，风向和风力，图标一，图标二。String(22) 被查询的城市或地区的介绍
        String url = "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx";
        //构造xml参数
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
                .append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">")
                .append("<soap:Body>")
                .append("<getWeatherbyCityName xmlns=\"http://WebXml.com.cn/\">")
                .append("<theCityName>")
                .append("57036")
                .append("</theCityName>")
                .append("</getWeatherbyCityName>")
                .append("</soap:Body>")
                .append("</soap:Envelope>");
        String outXml = HttpWebServiceUtil.sendWebService(url, "getWeatherbyCityName", "theCityName", sb, "");
        System.out.println(outXml);
    }
}
