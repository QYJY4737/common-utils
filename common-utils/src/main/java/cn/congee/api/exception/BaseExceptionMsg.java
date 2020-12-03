package cn.congee.api.exception;

/**
 * @Author: yang
 * @Date: 2020-12-03 10:53
 */
public enum BaseExceptionMsg implements IExceptionMsg {

    EXECUTE_OK(0, "执行成功"),
    EXECUTE_FAILD(-1, "执行失败"),
    ;

    private Integer code;
    private String message;

    BaseExceptionMsg(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
