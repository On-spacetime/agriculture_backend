package com.agriculture.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Seller implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    // 姓名
    private String name;

    // 密码
    private String password;

    // 电话
    private String phone;

    // 性别 1：男 0：女
    private String sex;

    // 身份证号
    private String id_number;

    // 头像
    private String avatar;
}
