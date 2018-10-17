package cn.itcast.travel.domain;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private int uid;
    private     String username;  //unique
    private     String password;
    private     String name;
    private     String birthday;
    private     String sex;  //不能使用 char类型，不知道为什么,反正就是不行，数据库是utf-8编码格式的时候就是不行
    private     String telephone;
    private     String email;
    private     String status;
    private     String code; //unique不能使用 char类型，不知道为什么,反正就是不行，数据库是utf-8编码格式的时候就是不行

    public User() {
    }


}

