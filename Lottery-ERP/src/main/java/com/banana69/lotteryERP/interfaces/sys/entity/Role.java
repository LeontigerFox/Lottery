package com.banana69.lotteryERP.interfaces.sys.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: banana69
 * @date: 2023/04/19/20:32
 * @description:
 */
@Data
@TableName("sys_role")
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private String roleKey;

    private Integer status;

    private Long createBy;

    private Date createTime;

    private Long updateBy;


    private Date updateTime;

    private String roleDesc;


    @TableLogic(value = "0", delval = "1")
    private Integer deleted;
}