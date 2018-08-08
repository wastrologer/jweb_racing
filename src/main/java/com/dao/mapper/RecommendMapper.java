package com.dao.mapper;

import com.pojo.Recommend;

import java.util.List;

public interface RecommendMapper {
    public List<Recommend> getRecommendByCondition(Recommend recommend)throws Exception;
    public Integer countRecommendByCondition(Recommend recommend)throws Exception;
    public Integer addRecommend(Recommend recommend)throws Exception;
    public Integer updateRecommend(Recommend recommend)throws Exception;
    public Integer deleteRecommendById(Integer id)throws Exception;
}
