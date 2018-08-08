package com.service.impl;

import com.common.cache.CacheClient;
import com.common.utils.SvcUtils;
import com.dao.mapper.CollectionMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pojo.Collection;
import com.pojo.User;
import com.service.ICollectionSvc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;
@Service
public class CollectionSvcImpl implements ICollectionSvc {
    private static final Logger logger = LoggerFactory.getLogger(CollectionSvcImpl.class);
    private Integer defaultPageSize=2;

    @Resource
    private CacheClient cacheClient;
    @Resource
    CollectionMapper collectionaaMapper;
    @Resource
    SvcUtils svcUtils;


    @Override
    public Integer countCollectionByCondition(com.pojo.Collection collection)throws Exception {
        return collectionaaMapper.countCollectionByCondition(collection);
    }

    @Override
    public Integer countCollectionByEssayId(Integer essayId)throws Exception {
        Collection collection=new Collection();
        collection.setEssayId(essayId);
        return countCollectionByCondition(collection);
    }

    @Override
    public PageInfo getCollectionByConditionAndPage(com.pojo.Collection collection, Integer pageNum, Integer pageSize)throws Exception {
        if(pageNum==null)
            pageNum=1;
        if(pageSize==null||pageSize<=0)
            pageSize=defaultPageSize;
        PageHelper.startPage(pageNum,pageSize);
        List<com.pojo.Collection> collectionList=collectionaaMapper.getCollectionByCondition(collection);
        PageInfo pageInfo=new PageInfo(collectionList);
        return pageInfo;
    }

    @Override
    public PageInfo getCollectionByConditionAndPage(com.pojo.Collection collection, Integer pageNum)throws Exception {
        return getCollectionByConditionAndPage(collection,pageNum,defaultPageSize);
    }

    @Override
    public Integer addCollection(User user, com.pojo.Collection collection)throws Exception {
        if(user==null)
            return null;
        collection.setUserId(user.getUserId());
        return addCollection(collection);
    }

    @Override
    public Integer addCollection(User user, Integer essayId)throws Exception {
        Collection collection=new Collection();
        collection.setEssayId(essayId);
        return addCollection(user,collection);
    }

    @Override
    public Integer updateCollection(User user, com.pojo.Collection collection)throws Exception {
        if(user==null||user.getUserId()!=collection.getUserId())
            return null;
        return updateCollection(collection);
    }

    @Override
    public Integer deleteCollectionByCondition(User user, com.pojo.Collection collection)throws Exception {
        Collection c=getCollectionByAccurateCondition(collection);
        if(collection.getEssayId()==null||user.getUserId()==null||user.getUserId()!=c.getUserId())
            return null;
        return deleteCollectionByCondition(collection);
    }

    @Override
    public Integer deleteCollectionByEssayId(int userId, int essayId)throws Exception {
        Collection collection=new Collection();
        collection.setEssayId(essayId);
        collection.setUserId(userId);
        return deleteCollectionByCondition(collection);
    }

    @Override
    public Integer addCollection(com.pojo.Collection collection)throws Exception {

        collection.setCreateTime(new Timestamp(System.currentTimeMillis()));
        collection.setCollectionTime(new Timestamp(System.currentTimeMillis()));
        int cNum=collectionaaMapper.addCollection(collection);
        logger.info("collectionMapper.addCollection结果为"+cNum);
        if(cNum!=1){
            throw new Exception(collection.toString());
        }
        return cNum;
    }



    @Override
    public com.pojo.Collection getCollectionByAccurateCondition(com.pojo.Collection collection)throws Exception {
        List<com.pojo.Collection> result=collectionaaMapper.getCollectionByCondition(collection);
        if(result.size()!=1){
            logger.info(collection.toString());
        }
        return svcUtils.judgeResultList(result);
    }

    @Override
    public Integer updateCollection(com.pojo.Collection collection)throws Exception {
        int i= collectionaaMapper.updateCollection(collection);
        if(i!=1){
            throw new Exception(collection.toString());
        }
        return i;
    }

    @Override
    public Integer deleteCollectionByCondition(Collection collection)throws Exception {
        int i= collectionaaMapper.deleteCollectionByCondition(collection);
        if(i!=1){
            throw new Exception(collection.toString());
        }
        return i;
    }

    public static Logger getLogger() {
        return logger;
    }

    public Integer getDefaultPageSize() {
        return defaultPageSize;
    }

    public void setDefaultPageSize(Integer defaultPageSize) {
        this.defaultPageSize = defaultPageSize;
    }

    public CacheClient getCacheClient() {
        return cacheClient;
    }

    public void setCacheClient(CacheClient cacheClient) {
        this.cacheClient = cacheClient;
    }

    public CollectionMapper getCollectionaaMapper() {
        return collectionaaMapper;
    }

    public void setCollectionaaMapper(CollectionMapper collectionaaMapper) {
        this.collectionaaMapper = collectionaaMapper;
    }

    public SvcUtils getSvcUtils() {
        return svcUtils;
    }

    public void setSvcUtils(SvcUtils svcUtils) {
        this.svcUtils = svcUtils;
    }
}
