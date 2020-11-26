package com.qf.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

@Data
@TableName("t_user")
public class User {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private String email;

    private Integer sex;

    private String phone;
}
