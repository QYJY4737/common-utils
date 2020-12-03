package cn.congee.api.exception;

/**
 * @Author: yang
 * @Date: 2020-12-03 11:14
 */
public class ApiException extends BaseException {

    public ApiException(Integer code, String errorMessage){
        super(code, errorMessage);
    }

    public ApiException(IExceptionMsg exception){
        super(exception);
    }

    public ApiException(Exception e){
        super(e);
    }

    public ApiException(String errMessage){
        super(errMessage);
    }
}
