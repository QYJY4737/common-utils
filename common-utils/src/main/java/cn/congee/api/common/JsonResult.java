package cn.congee.api.common;

import cn.congee.api.exception.BaseException;
import cn.congee.api.exception.BaseExceptionMsg;
import cn.congee.api.exception.IExceptionMsg;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.io.Serializable;

/**
 * @Author: yang
 * @Date: 2020-12-03 10:57
 */
@Slf4j
public class JsonResult<T> implements Serializable {

    @ApiModelProperty(value = "数据")
    private T data;

    @ApiModelProperty(value = "信息")
    private String message;

    @ApiModelProperty(value = "接口是否成功执行")
    private boolean success;

    @ApiModelProperty(value = "错误码")
    private int code;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static Logger getLog() {
        return log;
    }

    /**
     * 构造函数
     *
     */
    public JsonResult() {
        this.data = null;
        this.message = BaseExceptionMsg.EXECUTE_OK.getMessage();
        this.code = BaseExceptionMsg.EXECUTE_OK.getCode();
        this.success = (this.code == 0) ? true : false;
    }

    public JsonResult(T data) {
        this.data = data;
        this.message = BaseExceptionMsg.EXECUTE_OK.getMessage();
        this.code = BaseExceptionMsg.EXECUTE_OK.getCode();
        this.success = (this.code == 0) ? true : false;
    }

    public JsonResult(IExceptionMsg msg) {
        this.data = null;
        this.message = msg.getMessage();
        this.code = msg.getCode();
        this.success = (this.code == 0) ? true : false;
    }

    public JsonResult(int code, String message) {
        this.data = null;
        this.message = message;
        this.code = code;
        this.success = (this.code == 0) ? true : false;
    }

    public JsonResult(int code, String message, T data) {
        this.data = data;
        this.message = message;
        this.code = code;
        this.success = (this.code == 0) ? true : false;
    }

    public JsonResult(IExceptionMsg msg, T data) {
        this.data = data;
        this.message = msg.getMessage();
        this.code = msg.getCode();
        this.success = (msg.getCode() == 0) ? true : false;
    }

    public JsonResult(IExceptionMsg msg, String message) {
        this(msg);
        this.message = message;
    }

    public JsonResult(IExceptionMsg msg, String message, T data) {
        this(msg);
        this.message = message;
        this.data = data;
    }

    public JsonResult(BaseException baseException, T data) {
        this.data = data;
        this.message = baseException.getMessage();
        this.code = BaseExceptionMsg.EXECUTE_FAILD.getCode();
        this.success = false;
    }
}
