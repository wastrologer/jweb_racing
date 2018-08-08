package com.service.impl;

import com.common.cache.CacheClient;
import com.common.utils.SvcUtils;
import com.dao.mapper.CoinMapper;
import com.dao.mapper.GoldMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pojo.Coin;
import com.pojo.Gold;
import com.pojo.User;
import com.service.IGoldAndCoinSvc;
import com.service.IUserSvc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

@Service
public class GoldAndCoinSvcImpl implements IGoldAndCoinSvc {
    private static final Logger logger = LoggerFactory.getLogger(GoldAndCoinSvcImpl.class);
    private Integer defaultPageSize=99;

    @Resource
    private CacheClient cacheClient;
    @Resource
    GoldMapper goldMapper;    
    @Resource
    CoinMapper coinMapper;
    @Resource
    SvcUtils svcUtils;

    @Resource
    IUserSvc userSvcImpl;

    @Override
    public Integer countCoinByCondition(Coin coin)throws Exception {
        return coinMapper.countCoinByCondition(coin);
    }

    @Override
    public PageInfo getCoinByConditionAndPage(Coin coin, Integer pageNum, Integer pageSize)throws Exception {
        if(pageNum==null)
            pageNum=1;
        if(pageSize==null||pageSize<=0)
            pageSize=defaultPageSize;
        PageHelper.startPage(pageNum,pageSize);
        List<Coin> coinList= coinMapper.getCoinByCondition(coin);
        PageInfo pageInfo=new PageInfo(coinList);
        return pageInfo;
    }

    @Override
    public PageInfo getCoinByConditionAndPage(Coin coin, Integer pageNum)throws Exception {
        return getCoinByConditionAndPage(coin,pageNum,defaultPageSize);
    }
    @Transactional
    @Override
    public Integer addCoinWithUser(Integer userId, Integer coinNum,String reason)throws Exception {
        User user=new User();
        user.setUserId(userId);
        user.setUserCoin(coinNum);
        Integer i=userSvcImpl.updateUserOnly(user);
        logger.info("userSvcImpl.updateUserOnly(user)结果为:"+i);

        if(i!=null&&i==1) 
            return addCoinOnly(userId,coinNum,reason);
        else
            throw new Exception(user.toString());
    }



    @Override
    public Integer addCoinOnly(Integer userId, Integer coinNum,String reason)throws Exception {
        Coin coin=new Coin();
        coin.setCoinRecordTime(new Timestamp(System.currentTimeMillis()));
        coin.setCoinUserId(userId);
        coin.setCoinNum(coinNum);
        coin.setCoinReason(reason);
        coin.setCreateTime(new Timestamp(System.currentTimeMillis()));
        int cNum= coinMapper.addCoin(coin);
        logger.info("coinMapper.addCoin结果为"+cNum);
        return cNum;
    }



    @Override
    public Coin getCoinByAccurateCondition(Coin coin)throws Exception {
        List<Coin> result= coinMapper.getCoinByCondition(coin);
        if(result.size()!=1){
            logger.info(coin.toString());
        }
        return svcUtils.judgeResultList(result);
    }

    @Override
    public Integer updateCoin(Coin coin)throws Exception {
        int i= coinMapper.updateCoin(coin);
        if(i!=1){
            throw new Exception(coin.toString());
        }
        return  i;
    }
    
    
    @Override
    public Integer countGoldByCondition(Gold gold)throws Exception {
        return goldMapper.countGoldByCondition(gold);
    }

    @Override
    public PageInfo getGoldByConditionAndPage(Gold gold, Integer pageNum, Integer pageSize)throws Exception {
        if(pageNum==null)
            pageNum=1;
        if(pageSize==null||pageSize<=0)
            pageSize=defaultPageSize;
        PageHelper.startPage(pageNum,pageSize);
        List<Gold> goldList= goldMapper.getGoldByCondition(gold);
        PageInfo pageInfo=new PageInfo(goldList);
        return pageInfo;
    }

    @Override
    public PageInfo getGoldByConditionAndPage(Gold gold, Integer pageNum)throws Exception {
        return getGoldByConditionAndPage(gold,pageNum,defaultPageSize);
    }
    @Transactional
    @Override
    public Integer addGoldWithUser(Integer userId, Integer goldNum,String reason)throws Exception {
        User user=new User();
        user.setUserId(userId);
        user.setUserGold(goldNum);
        Integer i=userSvcImpl.updateUserOnly(user);
        logger.info("userSvcImpl.updateUserOnly(user)结果为:"+i);

        if(i!=null&&i==1)
            return addGoldOnly(userId,goldNum,reason);
        else
            throw new Exception(user.toString());
    }



    @Override
    public Integer addGoldOnly(Integer userId, Integer goldNum,String reason)throws Exception {
        Gold gold=new Gold();
        gold.setGoldRecordTime(new Timestamp(System.currentTimeMillis()));
        gold.setCreateTime(new Timestamp(System.currentTimeMillis()));
        gold.setGoldUserId(userId);
        gold.setGoldNum(goldNum);
        gold.setGoldReason(reason);
        int cNum= goldMapper.addGold(gold);
        logger.info("goldMapper.addGold结果为"+cNum);
        if(cNum!=1){
            throw new Exception(gold.toString());
        }
        return cNum;
    }



    @Override
    public Gold getGoldByAccurateCondition(Gold gold)throws Exception {
        List<Gold> result= goldMapper.getGoldByCondition(gold);
        if(result.size()!=1){
            logger.info(gold.toString());
        }
        return svcUtils.judgeResultList(result);
    }

    @Override
    public Integer updateGold(Gold gold)throws Exception {
        int i= goldMapper.updateGold(gold);
        if(i!=1){
            throw new Exception(gold.toString());
        }
        return  i;
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

    public GoldMapper getGoldMapper() {
        return goldMapper;
    }

    public void setGoldMapper(GoldMapper goldMapper) {
        this.goldMapper = goldMapper;
    }

    public CoinMapper getCoinMapper() {
        return coinMapper;
    }

    public void setCoinMapper(CoinMapper coinMapper) {
        this.coinMapper = coinMapper;
    }

    public SvcUtils getSvcUtils() {
        return svcUtils;
    }

    public void setSvcUtils(SvcUtils svcUtils) {
        this.svcUtils = svcUtils;
    }

    public IUserSvc getUserSvcImpl() {
        return userSvcImpl;
    }

    public void setUserSvcImpl(IUserSvc userSvcImpl) {
        this.userSvcImpl = userSvcImpl;
    }
}
