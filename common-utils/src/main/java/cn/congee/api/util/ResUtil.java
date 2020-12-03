package cn.congee.api.util;

import cn.congee.api.common.JsonResult;
import cn.congee.api.exception.ApiException;
import cn.congee.api.exception.BaseExceptionMsg;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: yang
 * @Date: 2020-12-03 10:23
 */

@Slf4j
@Component
public class ResUtil {

    /**
     * 获取结果
     *
     * @param res
     * @param <T>
     * @return
     */
    public static <T> T getRes(JsonResult<T> res){
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        log.info("\n\n----------------------------------------------\n" +
                 "\n类[{}]中的方法[{}]开始返回\n" +
                 "\n------------------------------------------------\n",
                  trace[2].getClassName(), trace[2].getMethodName());
        if(res.getSuccess()){
            return res.getData();
        }else {
            log.error("获取信息失败,返回值为:[{}]", JSON.toJSONString(res));
            throw new ApiException(BaseExceptionMsg.EXECUTE_FAILD.getCode(), res.getMessage());
        }
    }


}
