package com.banana69.lottery.common;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/15/20:01
 * @description:
 */
public class ResultData<T> implements Serializable {
    private Result result;
    private T data;

    public ResultData(Result result, T data) {
        this.result = result;
        this.data = data;
    }



    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> ResultData<T> success(T data) {
        return new ResultData<>(new Result(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo()), data);
    }

    public static <T> ResultData<T> fail(Constants.ResponseCode responseCode) {
        return new ResultData<>(new Result(responseCode.getCode(), responseCode.getInfo()), null);
    }

    public static <T> ResultData<T> fail(Constants.ResponseCode responseCode, T data) {
        return new ResultData<>(new Result(responseCode.getCode(), responseCode.getInfo()), data);
    }



    @Override
    public String toString() {
        return "ResultData{" +
                "result=" + result +
                ", data=" + data +
                '}';
    }

}
