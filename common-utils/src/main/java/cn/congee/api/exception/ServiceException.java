package cn.congee.api.exception;

/**
 * 业务逻辑异常封装
 *
 * @Author: yang
 * @Date: 2020-12-03 11:20
 */
public class ServiceException extends BaseException {

    public ServiceException(Integer code, String errorMessage){
        super(code, errorMessage);
    }

    public ServiceException(IExceptionMsg exception){
        super(exception);
    }

    public ServiceException(Exception e){
        super(e);
    }

    public ServiceException(String errorMessage){
        super(errorMessage);
    }

}
