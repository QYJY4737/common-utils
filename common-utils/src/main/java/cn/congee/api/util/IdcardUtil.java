package cn.congee.api.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 身份证工具类
 *
 * @Author: yang
 * @Date: 2020-12-03 16:24
 */
@Slf4j
public class IdcardUtil extends StringUtils {

    //常量值定义
    private static String TRUE_STR = "true";
    private static String A_STR = "A";
    private static String IDCARD_PATTERN_ADMEN = "^[1|5|7][0-9]{6}\\(?[0-9A-Z]\\)?&";
    private static String IDCARD_PATTERN_XIANGANG = "^[A-Z]{1,2}[0-9]{6}\\(?[0-9A]\\)?&";
    private static String IDCARD_PATTERN_TAIWAN = "^[a-zA-Z][0-9]{9}&";

    private static final Integer TWO = 2;
    private static final Integer EIGHT = 8;
    private static final Integer NINE = 9;
    private static final Integer TEN = 10;
    private static final Integer TWELVE = 12;

    //1-男 2-女
    private static final String M = "1";
    private static final String F = "2";

    //中国公民身份证号码最小/大长度
    public static final int CHINA_ID_MIN_LENGTH = 15;
    public static final int CHINA_ID_MAX_LENGTH = 18;

    //省、直辖市代码表
    public static final String[] CITY_CODE = {
            "11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36", "37", "41",
            "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65", "71",
            "81", "82", "91"
    };

    //每位加权因子
    public static final int[] POWER = {
            7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2
    };

    //第18位校验码
    public static final String[] VERIFY_CODE = {
            "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"
    };

    //最低年限
    public static final int MIN = 1930;
    public static Map<String, String> cityCodes = new HashMap<String, String>();

    //台湾身份首字母对应数字
    public static Map<String, Integer> twFirstCode = new HashMap<String, Integer>();

    //香港身份首字母对应数字
    public static Map<String, Integer> hkFirstCode = new HashMap<String, Integer>();

    static {
        cityCodes.put("11", "北京");
        cityCodes.put("12", "天津");
        cityCodes.put("13", "河北");
        cityCodes.put("14", "山西");
        cityCodes.put("15", "内蒙古");
        cityCodes.put("21", "辽宁");
        cityCodes.put("22", "吉林");
        cityCodes.put("23", "黑龙江");
        cityCodes.put("31", "上海");
        cityCodes.put("32", "江苏");
        cityCodes.put("33", "浙江");
        cityCodes.put("34", "安徽");
        cityCodes.put("35", "福建");
        cityCodes.put("36", "江西");
        cityCodes.put("37", "山东");
        cityCodes.put("41", "河南");
        cityCodes.put("42", "湖北");
        cityCodes.put("43", "湖南");
        cityCodes.put("44", "广东");
        cityCodes.put("45", "广西");
        cityCodes.put("46", "海南");
        cityCodes.put("50", "重庆");
        cityCodes.put("51", "四川");
        cityCodes.put("52", "贵州");
        cityCodes.put("53", "云南");
        cityCodes.put("54", "西藏");
        cityCodes.put("61", "陕西");
        cityCodes.put("62", "甘肃");
        cityCodes.put("63", "青海");
        cityCodes.put("64", "宁夏");
        cityCodes.put("65", "新疆");
        cityCodes.put("71", "台湾");
        cityCodes.put("81", "香港");
        cityCodes.put("82", "澳门");
        cityCodes.put("91", "国外");
        twFirstCode.put("A", 10);
        twFirstCode.put("B", 11);
        twFirstCode.put("C", 12);
        twFirstCode.put("D", 13);
        twFirstCode.put("E", 14);
        twFirstCode.put("F", 15);
        twFirstCode.put("G", 16);
        twFirstCode.put("H", 17);
        twFirstCode.put("J", 18);
        twFirstCode.put("K", 19);
        twFirstCode.put("L", 20);
        twFirstCode.put("M", 21);
        twFirstCode.put("N", 22);
        twFirstCode.put("P", 23);
        twFirstCode.put("Q", 24);
        twFirstCode.put("R", 25);
        twFirstCode.put("S", 26);
        twFirstCode.put("T", 27);
        twFirstCode.put("U", 28);
        twFirstCode.put("V", 29);
        twFirstCode.put("X", 30);
        twFirstCode.put("Y", 31);
        twFirstCode.put("W", 32);
        twFirstCode.put("Z", 33);
        twFirstCode.put("I", 34);
        twFirstCode.put("O", 35);
        hkFirstCode.put("A", 1);
        hkFirstCode.put("B", 2);
        hkFirstCode.put("C", 3);
        hkFirstCode.put("R", 18);
        hkFirstCode.put("U", 21);
        hkFirstCode.put("Z", 26);
        hkFirstCode.put("X", 24);
        hkFirstCode.put("W", 23);
        hkFirstCode.put("O", 15);
        hkFirstCode.put("N", 14);
    }

    /**
     * 15->18
     *
     * @param idCard
     * @return
     */
    public static String convert15CardTo18(String idCard){
        String idCard18 = "";
        if(idCard.length() != CHINA_ID_MIN_LENGTH){
            return null;
        }
        if(isNum(idCard)){
            //获取出生年月日
            String birthday = idCard.substring(6, 12);
            Date birthDate = null;
            try{
                birthDate = new SimpleDateFormat("yyMMdd").parse(birthday);
            }catch (ParseException e){
                e.printStackTrace();
            }
            Calendar cal = Calendar.getInstance();
            if(birthDate != null){
                cal.setTime(birthDate);
            }
            //获取出生年(完全表现形式,如:2010)
            String sYear = String.valueOf(cal.get(Calendar.YEAR));
            idCard18 = idCard.substring(0, 6) + sYear + idCard.substring(8);
            //转换字符数组
            char[] cArr = idCard18.toCharArray();
            if(cArr != null){
                int[] iCard = converCharToInt(cArr);
                int iSum17 = getPowerSum(iCard);
                //获取校验位
                String sVal = getCheckCode18(iSum17);
                if(sVal.length() > 0){
                    idCard18 += sVal;
                }else {
                    return null;
                }
            }
        }else {
            return null;
        }
        return idCard18;
    }

    /**
     * 数字验证
     *
     * @param val
     * @return
     */
    public static boolean isNum(String val){
        return val == null || "".equals(val) ? false : val.matches("^[0-9]*&");
    }

    /**
     * 字符数组->数字数组
     *
     * @param ca
     * @return
     */
    public static int[] converCharToInt(char[] ca){
        int len = ca.length;
        int[] iArr = new int[len];
        try{
            for (int i = 0; i < len; i++){
                iArr[i] = Integer.parseInt(String.valueOf(ca[i]));
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        return iArr;
    }

    /**
     * 将身份证的每位和对应位的加权因子相乘之后,再得到和值
     *
     * @param iArr
     * @return
     */
    public static int getPowerSum(int[] iArr){
        int iSum = 0;
        if(POWER.length == iArr.length){
            for (int i = 0; i < iArr.length; i++){
                for (int j = 0; j < POWER.length; j++){
                    if(i == j){
                        iSum = iSum + iArr[i] * POWER[j];
                    }
                }
            }
        }
        return iSum;
    }

    /**
     * 将power和值与11取模获得余数进行校验码判断
     *
     * @param iSum
     * @return
     */
    public static String getCheckCode18(int iSum){
        String sCode = "";
        switch (iSum % 11){
            case 10:
                sCode = "2";
                break;
            case 9:
                sCode = "3";
                break;
            case 8:
                sCode = "4";
                break;
            case 7:
                sCode = "5";
                break;
            case 6:
                sCode = "6";
                break;
            case 5:
                sCode = "7";
                break;
            case 4:
                sCode = "8";
                break;
            case 3:
                sCode = "9";
                break;
            case 2:
                sCode = "x";
                break;
            case 1:
                sCode = "0";
                break;
            case 0:
                sCode = "1";
                break;
            default:
                break;
        }
        return sCode;
    }

    /**
     * 验证身份证是否合法
     *
     * @param idCard
     * @return
     */
    public static boolean validateCard(String idCard){
        String card = idCard.trim();
        if(validateIdCard18(card)){
            return true;
        }
        if(validateIdCard15(card)){
            return true;
        }
        String[] cardval = validateIdCard10(card);
        if(cardval != null){
            return (TRUE_STR).equals(cardval[2]);
        }
        return false;
    }

    /**
     * 验证18位身份编码是否合法
     *
     * @param idCard
     * @return
     */
    public static boolean validateIdCard18(String idCard){
        boolean bTrue = false;
        if(idCard.length() == CHINA_ID_MAX_LENGTH){
            //前17位
            String code17 = idCard.substring(0, 17);
            //第18位
            String code18 = idCard.substring(17, CHINA_ID_MAX_LENGTH);
            if(isNum(code17)){
                char[] cArr = code17.toCharArray();
                if(cArr != null){
                    int[] iCard = converCharToInt(cArr);
                    int iSum17 = getPowerSum(iCard);
                    //获取校验位
                    String val = getCheckCode18(iSum17);
                    if(val.length() > 0){
                        if(val.equalsIgnoreCase(code18)){
                            bTrue = true;
                        }
                    }
                }
            }
        }
        return bTrue;
    }

    /**
     * 验证15位身份编码是否合法
     *
     * @param idCard
     * @return
     */
    public static boolean validateIdCard15(String idCard){
        if(idCard.length() != CHINA_ID_MIN_LENGTH){
            return false;
        }
        if(isNum(idCard)){
            String proCode = idCard.substring(0, 2);
            if(cityCodes.get(proCode) == null){
                return false;
            }
            String birthCode = idCard.substring(6, 12);
            Date birthDate = null;
            try{
                birthDate = new SimpleDateFormat("yy").parse(birthCode.substring(0, 2));
            }catch (ParseException e){
                e.printStackTrace();
            }
            Calendar cal = Calendar.getInstance();
            if(birthDate != null){
                cal.setTime(birthDate);
            }
            return valiDate(cal.get(Calendar.YEAR), Integer.valueOf(birthCode.substring(2, 4)),
                    Integer.valueOf(birthCode.substring(4, 6)));
        }else {
            return false;
        }
    }

    /**
     * 验证小于当前日期,是否有效
     *
     * @param iYear 待验证日期(年)
     * @param iMonth 待验证日期(月 1-12)
     * @param iDate 待验证日期(日)
     * @return 是否有效
     */
    public static boolean valiDate(int iYear, int iMonth, int iDate){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int datePerMonth;
        if(iYear < MIN || iYear >= year){
            return false;
        }
        if(iMonth < 1 || iMonth > TWELVE){
            return false;
        }
        switch (iMonth){
            case 4:
            case 6:
            case 9:
            case 11:
                datePerMonth = 30;
                break;
            case 2:
                boolean dm = ((iYear % 4 == 0 && iYear % 100 != 0) || (iYear % 400 == 0))
                        && (iYear > MIN && iYear < year);
                datePerMonth = dm ? 29 : 28;
                break;
            default:
                datePerMonth = 31;
        }
        return (iDate >= 1) && (iDate <= datePerMonth);
    }

    public static String[] validateIdCard10(String idCard){
        String[] info = new String[3];
        String card = idCard.replaceAll("[\\(|\\)]", "");
        if(EIGHT != card.length() && card.length() != NINE && idCard.length() != TEN){
            return null;
        }
        if(idCard.matches(IDCARD_PATTERN_TAIWAN)){
            //台湾
            info[0] = "台湾";
            String char2 = idCard.substring(1, 2);
            if(M.equals(char2)){
                info[1] = "M";
            }else if(F.equals(char2)){
                info[1] = "F";
            }else {
                info[1] = "N";
                info[2] = "false";
                return info;
            }
            info[2] = validateTWCard(idCard) ? "true" : "false";
            //澳门
        }else if(idCard.matches(IDCARD_PATTERN_ADMEN)){
            info[0] = "澳门";
            info[1] = "N";
            // TODO
            //香港
        }else if(idCard.matches(IDCARD_PATTERN_XIANGANG)){
            info[0] = "香港";
            info[1] = "N";
            info[2] = validateHKCard(idCard) ? "true" : "false";
        }else {
            return null;
        }
        return info;
    }

    /**
     * 验证台湾身份证
     *
     * @param idCard 身份证号码
     * @return 验证码是否符合
     */
    public static boolean validateTWCard(String idCard){
        String start = idCard.substring(0, 1);
        String mid = idCard.substring(1, 9);
        String end = idCard.substring(9, 10);
        Integer iStart = twFirstCode.get(start);
        Integer sum = iStart / 10 + (iStart % 10) * 9;
        char[] chars = mid.toCharArray();
        Integer iflag = 8;
        for (char c : chars){
            sum = sum + Integer.valueOf(c + "") * iflag;
            iflag--;
        }
        return (sum % 10 == 0 ? 0 : (10 - sum % 10)) == Integer.valueOf(end) ? true : false;
    }

    /**
     * 验证香港身份证号码(存在bug,部分特殊身份证无法检查)
     * <p>
     * 身份证前2位为英文字符,如果只出现一个英文字符则表示第一位是空格,对应数字58 前2位英文字符A-Z分别对应数字10-35
     * 最后一位校验码为0-9的数字加上字符"A","A"代表10
     * 将身份证号码全部转换为数字,分别对应乘9-1相加的总和,整除11则证件号码有效
     * </p>
     *
     * @param idCard
     * @return
     */
    public static boolean validateHKCard(String idCard){
        String card = idCard.replaceAll("[\\(|\\)]", "");
        Integer sum = 0;
        if(card.length() == NINE){
            sum = (Integer.valueOf(card.substring(0, 1).toUpperCase().toCharArray()[0]) - 55) * 9
                  + (Integer.valueOf(card.substring(1, 2).toUpperCase().toCharArray()[0]) - 55) * 8;
            card = card.substring(1, 9);
        }else {
            sum = 522 + (Integer.valueOf(card.substring(0, 1).toUpperCase().toCharArray()[0]) - 55) * 8;
        }
        String mid = card.substring(1, 7);
        String end = card.substring(7, 8);
        char[] chars = mid.toCharArray();
        Integer iflag = 7;
        for (char c : chars){
            sum = sum + Integer.valueOf(c + "") * iflag;
            iflag--;
        }
        if(A_STR.equals(end.toUpperCase())){
            sum = sum + 10;
        }else {
            sum = sum + Integer.valueOf(end);
        }
        return (sum % 11 == 0) ? true : false;
    }

    /**
     * 根据身份编号获取年龄
     *
     * @param idCard
     * @return
     */
    public static int getAgeByIdCard(String idCard){
        int iAge = 0;
        if(idCard.length() == CHINA_ID_MIN_LENGTH){
            idCard = convert15CardTo18(idCard);
        }
        String year = idCard.substring(6, 10);
        Calendar cal = Calendar.getInstance();
        int iCurrYear = cal.get(Calendar.YEAR);
        iAge = iCurrYear - Integer.valueOf(year);
        return iAge;
    }

    /**
     * 根据身份编号获取生日
     *
     * @param idCard
     * @return (yyyyMMdd)
     */
    public static String getBirthByIdCard(String idCard){
        Integer len = idCard.length();
        if(len < CHINA_ID_MIN_LENGTH){
            return null;
        }else if(len == CHINA_ID_MIN_LENGTH){
            idCard = convert15CardTo18(idCard);
        }
        return idCard.substring(6, 14);
    }

    /**
     * 根据身份编号获取生日年
     *
     * @param idCard
     * @return (yyyy)
     */
    public static Short getYearByIdCard(String idCard){
        Integer len = idCard.length();
        if(len < CHINA_ID_MIN_LENGTH){
            return null;
        }else if(len == CHINA_ID_MIN_LENGTH){
            idCard = convert15CardTo18(idCard);
        }
        return Short.valueOf(idCard.substring(6, 10));
    }

    /**
     * 根据身份编号获取生日月
     *
     * @param idCard
     * @return (MM)
     */
    public static Short getMonthByIdCard(String idCard){
        Integer len = idCard.length();
        if(len < CHINA_ID_MIN_LENGTH){
            return null;
        }else if(len == CHINA_ID_MIN_LENGTH){
            idCard = convert15CardTo18(idCard);
        }
        return Short.valueOf(idCard.substring(10, 12));
    }

    /**
     * 根据身份编号获取生日天
     *
     * @param idCard
     * @return (dd)
     */
    public static Short getDayByIdCard(String idCard){
        Integer len = idCard.length();
        if(len < CHINA_ID_MIN_LENGTH){
            return null;
        }else if(len == CHINA_ID_MIN_LENGTH){
            idCard = convert15CardTo18(idCard);
        }
        return Short.valueOf(idCard.substring(12, 14));
    }

    /**
     * 根据身份编号获取性别
     *
     * @param idCard
     * @return M-1-男 F-2-女
     */
    public static String getGenderByIdCard(String idCard){
        String sGender = "N";
        if(idCard.length() == CHINA_ID_MIN_LENGTH){
            idCard = convert15CardTo18(idCard);
        }
        String sCardNum = idCard.substring(16, 17);
        if(Integer.parseInt(sCardNum) % TWO != 0){
            sGender = "男";
        }else {
            sGender = "女";
        }
        return sGender;
    }

    /**
     * 根据身份编号获取户籍省份
     *
     * @param idCard
     * @return
     */
    public static String getProvinceByIdCard(String idCard){
        Integer len = idCard.length();
        String sProvince = null;
        String sProvinNum = "";
        if(len == CHINA_ID_MIN_LENGTH || len == CHINA_ID_MAX_LENGTH){
            sProvinNum = idCard.substring(0, 2);
        }
        sProvince = cityCodes.get(sProvinNum);
        return sProvince;
    }

    /**
     * 判断是否成年
     *
     * @param str
     * @return
     */
    public static boolean isAdult(String str){
        if(StringUtils.isEmpty(str) || (15 != str.length() && 18 != str.length())){
            return false;
        }else {
            String birthday = str.substring(6, str.length() - 4);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.parseInt(birthday.substring(0, 4)) + 18,
                    Integer.parseInt(birthday.substring(4, 6)) - 1,
                    Integer.parseInt(birthday.substring(6)), 23, 59);
            long l = System.currentTimeMillis() - calendar.getTimeInMillis();
            return 0 <= l;
        }
    }

    /**
     * 通过身份证获取某人的生日年份
     *
     * @param identifyNumber
     * @return
     */
    public static String getBirthDayByYear(String identifyNumber){
        String inputYear = "";
        if(identifyNumber.length() == 18){
            inputYear = identifyNumber.substring(6, 10);
        }
        return inputYear;
    }

    /**
     * 通过身份证获取某人的生日月份
     *
     * @param identifyNumber
     * @return
     */
    public static String getBirthDayByMonth(String identifyNumber){
        String inputMonth = "";
        if(identifyNumber.length() == 18){
            inputMonth = identifyNumber.substring(10, 12);
        }
        return inputMonth;
    }

    /**
     * 通过身份证获取某人的生日天数
     *
     * @param identifyNumber
     * @return
     */
    public static String getBirthDayByDay(String identifyNumber){
        String inputDay = "";
        if(identifyNumber.length() == 18){
            inputDay = identifyNumber.substring(12, 14);
        }
        return inputDay;
    }

    /**
     * 通过身份证获取某人的生日
     *
     * @param identifyNumber
     * @return
     */
    public static String getBirthday(String identifyNumber){
        String year = getBirthDayByYear(identifyNumber);
        String month = getBirthDayByMonth(identifyNumber);
        String day = getBirthDayByDay(identifyNumber);
        return year + "-" + month + "-" + day;
    }

}
