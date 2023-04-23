package com.banana69.lotteryERP.interfaces.sys.common.constant;

/**
 * Created with IntelliJ IDEA.

 * @author: banana69
 * @date: 2023/4/21/13:50
 * @description:  jwt 静态变量
 */
public class JwtConstant {

    /**
     * token
     */
    public static final int JWT_ERRCODE_NULL = 4000;			//Token不存在
    public static final int JWT_ERRCODE_EXPIRE = 4001;			//Token过期
    public static final int JWT_ERRCODE_FAIL = 4002;			//验证不通过

    /**
     * JWT
     */
    public static final String JWT_SECERT = "8677df7fc3a34e26a61c034d5ec8245d";			//密匙
    public static final long JWT_TTL = 24*60 * 60 * 1000;									//token有效时间
}
