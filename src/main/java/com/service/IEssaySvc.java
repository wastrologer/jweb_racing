package com.service;

import com.common.entity.ReturnMessage;
import com.github.pagehelper.PageInfo;
import com.pojo.*;

import java.util.List;

public interface IEssaySvc {
    public PageInfo getEssayByCollectionAndPage(Collection collection, Integer pageNum, Integer pageSize)throws Exception;
    public PageInfo getEssayByConditionAndPage(EssayParam essayParam, Integer pageNum, Integer pageSize)throws Exception;
    public PageInfo getSimpleEssayByConditionAndPage(EssayParam essayParam, Integer pageNum, Integer pageSize)throws Exception;
    public PageInfo getEssayByConcernAndPage(Integer userId, Integer pageNum)throws Exception;
    public PageInfo getEssayByConcernAndPage(Integer userId,Integer pageNum,Integer pageSize)throws Exception;
    public PageInfo getEssayByUserListAndPage(List<Integer> list,Integer isPublished, Integer pageNum)throws Exception;
    public Integer countEssayByCondition(Essay essay)throws Exception;
    public PageInfo getEssayByFuzzyConditionAndPage(Essay essay, Integer pageNum)throws Exception;
    public PageInfo getEssayByFuzzyConditionAndPage(Essay essay,Integer pageNum,Integer pageSize)throws Exception;
    public Essay getEssayByAccurateCondition(Essay essay)throws Exception;
    public Essay getEssayByEssayId(Integer id)throws Exception;

    public Integer addEssay(User user, Essay essay)throws Exception;
    public Integer updateEssayClickNum(ReturnMessage msg)throws Exception;
    public Integer updateEssayCommentNum(ReturnMessage msg)throws Exception;
    public Integer updateEssayRecommendNum(ReturnMessage msg)throws Exception;


    public Integer updateEssaySelf(User user, Essay essay)throws Exception;
    public Integer updateEssayByOthers( Essay essay)throws Exception;
    public Integer updateEssay(Essay essay)throws Exception;
    public Integer updateEssayBatch(User user)throws Exception;
    public Integer deleteEssayById(Integer id)throws Exception;
    public PageInfo getEssayByUserListAndPage(List<Integer> ids, Integer isPublished, Integer num, Integer size)throws Exception;

    public PageInfo getTopicByConditionAndPage(Topic topic, Integer pageNum, Integer pageSize)throws Exception;

}
