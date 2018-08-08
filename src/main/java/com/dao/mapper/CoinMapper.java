package com.dao.mapper;

import com.pojo.Coin;

import java.util.List;

public interface CoinMapper {
    public List<Coin> getCoinByCondition(Coin coin)throws Exception;
    public Integer countCoinByCondition(Coin coin)throws Exception;
    public Integer addCoin(Coin coin)throws Exception;
    public Integer updateCoin(Coin coin)throws Exception;
    public Integer deleteCoinById(Integer id)throws Exception;
}
