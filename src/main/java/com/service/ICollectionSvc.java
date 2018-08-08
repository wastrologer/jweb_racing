package com.service;

import com.github.pagehelper.PageInfo;
import com.pojo.Collection;
import com.pojo.User;

public interface ICollectionSvc {
    public Integer countCollectionByCondition(Collection collection)throws Exception;
    public Collection getCollectionByAccurateCondition(Collection collection)throws Exception;
    public PageInfo getCollectionByConditionAndPage(Collection collection, Integer pageNum, Integer pageSize)throws Exception;
    public PageInfo getCollectionByConditionAndPage(Collection collection, Integer pageNum)throws Exception;
    public Integer addCollection(User user, Collection collection)throws Exception;
    public Integer addCollection(User user, Integer essayId)throws Exception;
    public Integer countCollectionByEssayId(Integer essayId)throws Exception;

    public Integer updateCollection(User user, Collection collection)throws Exception;
    public Integer deleteCollectionByCondition(Collection collection)throws Exception;
    public Integer addCollection(Collection collection)throws Exception;
    public Integer updateCollection(Collection collection)throws Exception;
    public Integer deleteCollectionByCondition(User user, Collection collection)throws Exception;
    public Integer deleteCollectionByEssayId(int userId, int essayId)throws Exception;


}
