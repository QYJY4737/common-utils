package cn.congee.api.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 用来转换json的工具类
 *
 * @Author: yang
 * @Date: 2020-12-03 15:57
 */
public class JsonUtil {

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
    }

    //获取ObjectMapper
    public static ObjectMapper getMapper(){ return mapper; }

    /**
     * obj->json
     *
     * @param obj
     * @return
     */
    public static String toJson(Object obj){
        try{
            Assert.notNull(obj, "obj can not be null");
            return mapper.writeValueAsString(obj);
        }catch (JsonProcessingException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * string->obj
     *
     * @param content
     * @param cls
     * @param <T>
     * @return
     */
    public static <T extends Object> T readJson(String content, Class<T> cls){
        try{
            return mapper.readValue(content, cls);
        }catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException("转换失败");
        }
    }

    /**
     * map->obj
     *
     * @param map
     * @param cls
     * @param <T>
     * @return
     */
    public static <T extends Object> T readJson(Map map, Class<T> cls){
        if(map != null){
            return readJson(toJson(map), cls);
        }
        return null;
    }

    /**
     * json->map
     *
     * @param content
     * @return
     */
    public static Map<String, Object> readMap(String content){
        try{
            return mapper.readValue(content, Map.class);
        }catch (JsonProcessingException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static List readList(String json, Class cls){
        try{
            return (List)getMapper().readValue(json, getCollectionType(ArrayList.class, cls));
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取JavaType
     *
     * @param collectionClass
     * @param elementClasses
     * @return
     */
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses){
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

}
