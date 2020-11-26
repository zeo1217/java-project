package com.qf.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@TableName("t_order")
public class Order {

    private String id;

    private Integer uid;

    @TableField(value = "total_price")
    private BigDecimal totalPrice;

    private String name;

    private String address;

    private String mobile;

    private Integer status;

    @TableField(value = "create_date")
    private Date createDate;

    @TableField(exist = false)
    private List<OrderDetail> orderDetailList;
}
