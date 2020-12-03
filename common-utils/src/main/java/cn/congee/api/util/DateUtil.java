package cn.congee.api.util;

import cn.congee.api.exception.ServiceException;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间转换工具类
 *
 * @Author: yang
 * @Date: 2020-12-03 18:55
 */
public class DateUtil {

    private static final FastDateFormat DATE_FORMATTER = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
    private static final FastDateFormat BASE_DATE_FORMATTER = FastDateFormat.getInstance("yyyy-MM-dd");
    private static final FastDateFormat BASE_DATE_FORMATTER_1 = FastDateFormat.getInstance("yyyyMMdd");
    private static final FastDateFormat MONTH_FORMATTER = FastDateFormat.getInstance("yyyy-MM");
    private static final FastDateFormat GAPLESS_DATE_FORMATTER = FastDateFormat.getInstance("yyyyMMddHHmmss");
    private static final FastDateFormat MILLIS_DATE_FORMATTER = FastDateFormat.getInstance("yyyyMMddHHmmssS");
    private static final FastDateFormat HOUR_MINUTE_DATE_FORMATTER = FastDateFormat.getInstance("HH:mm");
    private static final FastDateFormat HOUR_MINUTE_FORMATTER = FastDateFormat.getInstance("yyyy-MM-dd HH:mm");
    private static final FastDateFormat HOUR_MINUTE_OPERATION_FORMATTER = FastDateFormat.getInstance("yyyy/MM/dd");
    private static final FastDateFormat COORDINATE_HOUR_MINUTE_OPERATION_FORMATTER = FastDateFormat.getInstance("MM/dd");

    private static final int[] SEASON_1 = {0, 1, 2};
    private static final int[] SEASON_2 = {3, 4, 5};
    private static final int[] SEASON_3 = {6, 7, 8};
    private static final int[] SEASON_4 = {9, 10, 11};

    public enum DATE_PATTERN{

        yyyy_MM_dd_HH_mm_ss2("yyyy-MM-dd HH:mm:ss", "^\\d{2,4}-\\d{1,2}-\\d{1,2}\\s.{1,2}\\d{1,2}:\\d{1,2}:\\d{1,2}$"),
        yyyy_MM_dd2("yyyy-MM-dd", "^\\d{2,4}-\\d{1,2}-\\d{1,2}$"),
        yyyyMMdd("yyyyMMdd", "^\\d{2,4}\\d{1,2}\\d{1,2}$"),
        yyyy_MM2("yyyy-MM", "^\\d{2,4}-\\d{1,2}$"),
        yyyyMMddHHmmss("yyyyMMddHHmmss", "^\\d{2,4}\\d{1,2}\\d{1,2}\\s.{1,2}\\d{1,2}\\d{1,2}\\d{1,2}$"),
        yyyyMMddHHmmssS("yyyyMMddHHmmssS", "^\\d{2,4}\\d{1,2}\\d{1,2}\\s.{1,2}\\d{1,2}\\d{1,2}\\d{1,2}\\d{1,}$"),
        HH_mm("HH:mm", "^\\s.{1,2}\\d{1,2}:\\d{1,2}$"),
        yyyy_MM_dd_HH_mm("yyyy-MM-dd HH:mm", "^\\d{2,4}-\\d{1,2}-\\d{1,2}\\s.{1,2}\\d{1,2}:\\d{1,2}$"),
        yyyy_MM_dd("yyyy/MM/dd", "^\\d{2,4}/\\d{1,2}/\\d{1,2}$"),
        MM_dd("MM/dd", "^\\d{1,2}/\\d{1,2}$"),
        ;

        private String value;

        private String pattern;

        private DATE_PATTERN(String value, String pattern){
            this.value = value;
            this.pattern = pattern;
        }

        /**
         * 根据样本获得其样式
         *
         * @param date
         * @return
         */
        public static DATE_PATTERN getPatternBySample(String date){
            if(date != null){
                date = date.trim();
                for (DATE_PATTERN value : DATE_PATTERN.values()){
                    if(date.matches(value.pattern)){
                        return value;
                    }
                }
            }
            throw new ServiceException("日期为空或是不支持的样本样式: " + date);
        }

        @Override
        public String toString() {
            return "DATE_PATTERN{" +
                    "value='" + value + '\'' +
                    ", pattern='" + pattern + '\'' +
                    '}';
        }

    }

    /**
     * 解析任意受支持格式的时间
     *
     * @param date
     * @return
     */
    @SuppressWarnings("deprecation")
    public static Date parseDate(String date){
        if(date == null){
            return null;
        }
        date = date.trim();
        if(date.indexOf("中国标准时间") != -1 || date.indexOf("CSF") != -1){
            return new Date(Date.parse(date));
        }
        return parseDate(date, getPatternBySample(date));
    }

    /**
     * 根据指定模式解析时间字符串为date对象并返回
     *
     * @param date
     * @param pattern
     * @return
     */
    public static Date parseDate(String date, DATE_PATTERN pattern){
        try{
            DateFormat df = new SimpleDateFormat(pattern.toString());
            return df.parse(date);
        }catch (Exception e){
            throw new ServiceException(e);
        }
    }

    /**
     * 根据日期样品获得其样式
     *
     * @param sample
     * @return
     */
    public static DATE_PATTERN getPatternBySample(String sample){
        return DATE_PATTERN.getPatternBySample(sample);
    }

    /**
     * 日期比较
     *
     * @param date1
     * @param date2
     * @return 1>2->1;1=2->0;1<2->-1
     */
    public static int compareDate(Date date1, Date date2){
        if(date1.getTime() > date2.getTime()){
            return 1;
        }else if(date1.getTime() == date2.getTime()){
            return 0;
        }else {
            return -1;
        }
    }

    /**
     * 格式化时间
     * date->String
     *
     * @param date
     * @param date_pattern
     * @return
     */
    public static String formatDate(Date date, DATE_PATTERN date_pattern){
        DateFormat df = new SimpleDateFormat(date_pattern.toString());
        return df.format(date);
    }

}
