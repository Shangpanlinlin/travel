package cn.itcast.travel.domain;

import lombok.Data;

@Data
public class Category {
    private Integer cid;
    private String cname;
    public Category(String name){
        cname = name;
    }

    public Category(){

    }

    public Category(int score, String element) {
        this.cid = score;
        this.cname = element;
    }
}
