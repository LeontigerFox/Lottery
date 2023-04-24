package com.banana69.lotteryERP.interfaces.sys.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author banana69
 * @since 2023-04-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.NONE)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String username;

    private String password;

    private String email;

    private String phone;

    private Integer status;

    private String avatar;

    /**
     * 用户类型（0 管理员，1 普通用户）
     */
    private String userType;

    @TableLogic(value = "0", delval = "1")
    private Integer deleted;

    private String uid;

    @TableField(value = "false")
    private  String role;





}
