package cn.itcast.travel.domain;

import lombok.Data;

@Data
public class RouteImg {

    private Integer rgid ; // not null, auto increment primary key
    private Integer rid ;  // not null,
    private String bigPic; //  not null
    private String smallPic;  //


}
