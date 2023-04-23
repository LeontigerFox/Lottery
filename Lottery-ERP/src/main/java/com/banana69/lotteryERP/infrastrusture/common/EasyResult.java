package com.banana69.lotteryERP.infrastrusture.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;


/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/14/17:54
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EasyResult<T>  {
    //extends HashMap<String, Object>

    private Integer code;
    private String message;
    private T data;

    public static <T> EasyResult<T> success(){
        return new EasyResult<>(20000,"success",null);
    }

    public static <T> EasyResult<T> success(T data){
        return new EasyResult<>(20000,"success",data);
    }

    public static <T> EasyResult<T> success(T data,String message){
        return new EasyResult<>(20000,message,data);
    }

    public static <T> EasyResult<T> success(String message){
        return new EasyResult<>(20000,message,null);
    }

    public static<T>  EasyResult<T> fail(){
        return new EasyResult<>(20001,"fail",null);
    }

    public static<T>  EasyResult<T> fail(Integer code){
        return new EasyResult<>(code,"fail",null);
    }

    public static<T>  EasyResult<T> fail(Integer code, String message){
        return new EasyResult<>(code,message,null);
    }

    public static<T>  EasyResult<T> fail( String message){
        return new EasyResult<>(20001,message,null);
    }

//    @Override
//    public EasyResult<T> put(String key, Object value) {
//        super.put(key, value);
//        return this;
//    }


}
