package com.qf.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-15
 */
@Data
@TableName("t_car")
public class Car {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer gid;

    private Integer uid;

    private Integer sum;

    @TableField(value ="sub_total")
    private BigDecimal subTotal;

    @TableField(exist = false) //表中没有这一列
    private Goods goods; //商品信息

}
