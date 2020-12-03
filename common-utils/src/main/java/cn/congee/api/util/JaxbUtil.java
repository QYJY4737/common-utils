package cn.congee.api.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;

/**
 * xml转换工具类
 *
 * @Author: yang
 * @Date: 2020-12-03 16:11
 */
public class JaxbUtil {

    /**
     * javabean->xml
     *
     * @param obj
     * @param encoding
     * @return
     */
    public static String convertToXml(Object obj, String encoding){
        String result = null;
        try{
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            //隐去报文头的生成,Marshaller.JAXB_FRAGMENT默认为false
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

            MarshallerListener marlistener = new JaxbUtil().new MarshallerListener();
            marshaller.setListener(marlistener);

            StringWriter writer = new StringWriter();
            marshaller.marshal(obj, writer);
            result = writer.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * xml->javabean
     *
     * @param xml
     * @param c
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T converyToJavaBean(String xml, Class<T> c){
        T t = null;
        try{
            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            t = (T)unmarshaller.unmarshal(new StringReader(xml));
        }catch (Exception e){
            e.printStackTrace();
        }
        return t;
    }

    class MarshallerListener extends Marshaller.Listener{
        public static final String BLANK_CHAR = "";

        @Override
        public void beforeMarshal(Object source) {
            super.beforeMarshal(source);
            Field[] fields = source.getClass().getDeclaredFields();
            for (Field f : fields){
                f.setAccessible(true);
                try{
                    if(f.getType() == String.class && f.get(source) == null){
                        f.set(source, BLANK_CHAR);
                    }
                }catch (IllegalAccessException e){
                    e.printStackTrace();
                }
            }
        }
    }

}
