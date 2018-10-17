package cn.itcast.travel.domain;

import lombok.Data;

import java.util.List;

@Data
public class PageBean<T> {
    private int totalCount;  //查数据库
    private int totalPage;  //总页数，计算出来
    private int currentPage;  //当前页码
    private int pageSize;  //一页里面的记录数
    private List<T> contentList; //查数据库后每一页显示的数据集合

    public PageBean(){}

    public PageBean(int totalCount, int totalPage , int currentPage, int pageSize, List<T> contentList){
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.contentList = contentList;
    }
}
