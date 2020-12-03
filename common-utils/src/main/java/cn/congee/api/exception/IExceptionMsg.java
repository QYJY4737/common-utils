package cn.congee.api.exception;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: yang
 * @Date: 2020-12-03 10:25
 */
public interface IExceptionMsg {

    @ApiModelProperty(value = "获取异常的状态码")
    Integer getCode();

    @ApiModelProperty(value = "获取异常的提示信息")
    String getMessage();

}
