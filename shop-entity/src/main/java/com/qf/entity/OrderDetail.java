package com.qf.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("t_order_detail")
public class OrderDetail {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String oid;

    private Integer gid;

    private String gname;

    private String gdesc;

    private String gpic;

    private Integer gsum;

    private BigDecimal gprice;

    @TableField(value = "sub_total")
    private BigDecimal subTotal;
}
