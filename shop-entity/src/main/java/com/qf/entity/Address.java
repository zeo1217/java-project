package com.qf.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-17
 */
@Data
@TableName("t_address")
public class Address {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private String mobile;

    private String address;

    private Integer uid;

    private Integer def;
}
