package com.service;

import com.github.pagehelper.PageInfo;
import com.pojo.Coin;
import com.pojo.Gold;

public interface IGoldAndCoinSvc {
    public Integer countCoinByCondition(Coin coin)throws Exception;
    public Coin getCoinByAccurateCondition(Coin coin)throws Exception;
    public PageInfo getCoinByConditionAndPage(Coin coin, Integer pageNum, Integer pageSize)throws Exception;
    public PageInfo getCoinByConditionAndPage(Coin coin, Integer pageNum)throws Exception;

    public Integer addCoinOnly(Integer userId, Integer coinNum,String reason)throws Exception;
    public Integer addCoinWithUser(Integer userId, Integer coinNum,String reason)throws Exception;
    public Integer updateCoin(Coin coin)throws Exception;

    public Integer countGoldByCondition(Gold gold)throws Exception;
    public Gold getGoldByAccurateCondition(Gold gold)throws Exception;
    public PageInfo getGoldByConditionAndPage(Gold gold, Integer pageNum, Integer pageSize)throws Exception;
    public PageInfo getGoldByConditionAndPage(Gold gold, Integer pageNum)throws Exception;

    public Integer addGoldOnly(Integer userId, Integer goldNum,String reason)throws Exception;
    public Integer addGoldWithUser(Integer userId, Integer goldNum,String reason)throws Exception;

    public Integer updateGold(Gold gold)throws Exception;
}
