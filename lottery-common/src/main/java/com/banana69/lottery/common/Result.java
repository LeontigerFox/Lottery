package com.banana69.lottery.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统一返回对象中，Code码、Info描述
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result implements Serializable {
    private static final long serialVersionUID = -3826891916021780628L;
    private String code;
    private String info;

    public static Result buildResult(Constants.ResponseCode code, String info) {
        return new Result(code.getCode(), info);
    }

    public static Result buildResult(Constants.ResponseCode code, Constants.ResponseCode info) {
        return new Result(code.getCode(), info.getInfo());
    }

    public static Result buildSuccessResult() {
        return new Result(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo());
    }

    public static Result buildErrorResult() {
        return new Result(Constants.ResponseCode.UNKNOWN_ERROR.getCode(), Constants.ResponseCode.UNKNOWN_ERROR.getInfo());
    }

    public static Result buildErrorResult(String info) {
        return new Result(Constants.ResponseCode.UNKNOWN_ERROR.getCode(), info);
    }






}
