package cn.itcast.travel.domain;


import lombok.Data;

import java.util.Date;

@Data
public class Favorate {
    private Integer rid; //not null  primary key
    private Integer uid;// not null  primary key
    private Date date; // not null


}