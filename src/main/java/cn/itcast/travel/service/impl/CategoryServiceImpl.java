package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();
    private Jedis jedis = JedisUtil.getJedis();

    /**
     * 用缓存优化后的service
     * @return
     */
    @Override
    public List<Category> findAll() {

        //再 service 层加redis的访问
        //首先访问 redis 看是否有"category" 为key的排序集合数据
//        jedis.auth("foobar");
        //Set<String> categories =  jedis.zrange("category",0,-1);
        Set<Tuple> categories =  null;
        try{
            categories = jedis.zrangeWithScores("category", 0 , -1);
        }catch (Exception e){
            e.printStackTrace();
            jedis.resetState();
        }

        List<Category>  categoriesList = null;
        if(categories == null || categories.size() == 0){
            //第一次访问，需要去数据库取出数据
            System.out.println("从数据库查询");
            categoriesList = categoryDao.findAll();
            //数据库dao层查询结果返回后 ，把查询结果存储到redis中去
            for (Category category : categoriesList) {
                jedis.zadd("category", category.getCid(),category.getCname());
            }

        }else{
            System.out.println("从缓存里查询");
            //如果缓存里面有数据,把数据封装的List<Category>中去
            categoriesList  = new LinkedList<>();
            for (Tuple s : categories) {
              //  System.out.println(s);
                categoriesList.add(new Category( (int)s.getScore(),s.getElement()));
               // System.out.println(categoriesList.toString());
            }
        }

        return categoriesList;
    }
}
