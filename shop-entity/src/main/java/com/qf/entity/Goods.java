package com.qf.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-05
 */
@Data
@TableName("t_goods")
public class Goods implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String gname;

    private String gdesc;

    private BigDecimal gprice;

    private Integer gstock;

    private String gpic;

    private Integer gtype;
}
