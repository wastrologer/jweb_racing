package com.dao.mapper;

import com.pojo.Collection;

import java.util.List;

public interface CollectionMapper {
    public List<Collection> getCollectionByCondition(Collection collection)throws Exception;
    public Integer countCollectionByCondition(Collection collection)throws Exception;
    public Integer addCollection(Collection collection)throws Exception;
    public Integer updateCollection(Collection collection)throws Exception;
    public Integer deleteCollectionByCondition(Collection collection)throws Exception;
}
