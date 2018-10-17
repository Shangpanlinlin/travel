package cn.itcast.travel.domain;


import lombok.Data;

@Data
public class Seller {
    private Integer sid;  //not null, auto_increment, primary key
    private String sname;  // not null  unique
    private String consphone;  //not null
    private String address;



}
