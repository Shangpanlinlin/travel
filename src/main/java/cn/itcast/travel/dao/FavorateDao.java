package cn.itcast.travel.dao;

public interface FavorateDao {
    boolean findByRidAndUid(int rid, int uid);

    int findFavoriteCountByRid(Integer rid);

    void addFavorite(int rid, int uid);
}
