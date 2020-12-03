package cn.congee.api.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author: yang
 * @Date: 2020-12-03 20:53
 */
@Slf4j
@Component
public class AppContextUtil implements ApplicationContextAware {

    //定义静态ApplicationContext
    private static ApplicationContext applicationContext = null;

    /**
     * 重写接口的方法,该方法的参数为框架自动加载的IOC容器对象
     * 该方法在启动项目的时候会自动执行
     *
     * @param applicationContext ioc容器
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //将框架加载的ioc赋值给全局静态ioc
        AppContextUtil.applicationContext = applicationContext;
        log.info("==============================ApplicationContext加载成功===========================");
    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    //通过name获取bean
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    //通过class获取bean
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及class返回指定的bean
    public static <T> T getBean(String name, Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }

}
