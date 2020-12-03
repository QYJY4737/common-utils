package cn.congee.api.util;

import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: yang
 * @Date: 2020-12-03 12:15
 */
@Slf4j
@Component
public class PatternUtil {

    private static Pattern PATTERN = Pattern.compile("[\u4e00-\u9fa5]");

    private static Pattern IP_PATTERN = Pattern.compile("^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d[1-9])" +
                                                        "\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)"+
                                                        "\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)"+
                                                        "\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$");

    /**
     * list判空
     *
     * @param list
     * @return
     */
    public static boolean isEmptyList(List<?> list){
        return (null == list || list.size() == 0);
    }

    /**
     * list判空
     *
     * @param list
     * @return
     */
    public static boolean isNotEmptyList(List<?> list){
        return !isEmptyList(list);
    }

    /**
     * map判空
     *
     * @param map
     * @return
     */
    public static boolean isEmptyMap(Map<?, ?> map){
        return (null == map || map.size() == 0);
    }

    /**
     * map判空
     *
     * @param map
     * @return
     */
    public static boolean isNotEmptyMap(Map<?, ?> map){
        return !isEmptyMap(map);
    }

    /**
     * set判空
     *
     * @param set
     * @return
     */
    public static boolean isEmptySet(Set<?> set){
        return (null == set || set.size() == 0);
    }

    /**
     * set判空
     *
     * @param set
     * @return
     */
    public static boolean isNotEmptySet(Set<?> set){
        return !isEmptySet(set);
    }

    /**
     * 数组判空
     *
     * @param objs
     * @return
     */
    public static boolean isEmptyArray(Object[] objs){
        return (null == objs || objs.length == 0);
    }

    /**
     * 数组判空
     *
     * @param objs
     * @return
     */
    public static boolean isNotEmptyArray(Object[] objs){
        return !isEmptyArray(objs);
    }

    /**
     * 对象判空
     *
     * @param obj
     * @return
     */
    public static boolean isEmptyObj(Object obj){
        if(null == obj){
            return true;
        }else if(obj instanceof CharSequence){
            return ((CharSequence)obj).length() == 0;
        }else if(obj instanceof Collection){
            return ((Collection)obj).isEmpty();
        }else if(obj.getClass().isArray()){
            return Array.getLength(obj) == 0;
        }
        return false;
    }

    /**
     * 判断是否是夏季
     *
     * @param checkDate
     * @return
     */
    public static String isSummer(String checkDate){
        Calendar calendar = Calendar.getInstance();
        String start = calendar.get(Calendar.YEAR) + "-05-01";
        String end = calendar.get(Calendar.YEAR) + "-10-01";
        if(checkDate.compareTo(start) > 0 && checkDate.compareTo(end) < 0){
            return "夏季";
        }
        return "冬季";
    }

    /**
     * 获取startDate未来第past天的日期
     *
     * @param startDate
     * @param past
     * @return
     */
    public static String getFutureDate(String startDate, int past){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Objects.requireNonNull(DateUtil.parseDate(startDate)));
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(today);
    }

    /**
     * 将任意字符串转成指定长度字符串并以...结尾
     *
     * @param str 原始字符串
     * @param length
     * @return 截取后的字符串
     */
    public static String subStr(String str, Integer length){
        if(StringUtils.isNotBlank(str)){
            if(str.length() > length){
                return str.substring(0, str.length() - 4) + "...";
            }
        }
        return str;
    }

    /**
     * 判断字符串中是否包含中文
     * 不能校验是否为中文标点符号
     *
     * @param str 待校验字符串
     * @return
     */
    public static boolean isContainChinese(String str){
        Matcher matcher = PATTERN.matcher(str);
        return matcher.find();
    }

    /**
     * 判断ip是否有效
     *
     * @param ip
     * @return
     */
    public static boolean isValidIp(String ip){
        Matcher matcher = IP_PATTERN.matcher(ip);
        return matcher.matches();
    }

    /**
     * 返回对应字符的长度
     *
     * @param value
     * @return
     */
    public static int length(String value){
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        //获取字段值的长度,如果含中文字符,则每个中文字符长度为2,否则为1
        for (int i = 0; i < value.length(); i++){
            //获取一个字符
            String temp = value.substring(i, i + 1);
            //判断是否为中文字符
            if(temp.matches(chinese)){
                //中文字符长度为2
                valueLength += 2;
            }else {
                //其他字符长度为1
                valueLength += 1;
            }
        }
        return valueLength;
    }


}
