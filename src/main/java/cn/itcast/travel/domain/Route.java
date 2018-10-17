package cn.itcast.travel.domain;


import lombok.Data;

import java.util.List;

@Data
public class Route {

    private Integer rid;  //auto_increment not null  primary key
    private String  rname;  //not null
    private double price;  //not null
    private String routeIntroduce ;
    private String rflag; // not null 是否上架的记号，0没有上架，1代表上架
    private String rdate;
    private String isThemeTour; // not null 是否主题旅游，0不是，1是
    private Integer count; // defauil 0 收藏数量
    private Integer cid; // not null 分类id
    private String rimage;  //缩略图
    private Integer sid; //所属商家id
    private String sourceId;  //unique 抓取数据的来源id
    ////------以上是数据库字段


    private Category category;
    //根据cid联动查询出关联的类，数据库表并不保存这部分信息，因为数据库范式2是不允许冗余的
    private Seller seller;
    //所属商家
    private List<RouteImg> routeImgList;
    //商品详情图片列表


        public Route(){

        }


}
