package com.dao.mapper;

import com.pojo.Gold;

import java.util.List;

public interface GoldMapper {
    public List<Gold> getGoldByCondition(Gold gold)throws Exception;
    public Integer countGoldByCondition(Gold gold)throws Exception;
    public Integer addGold(Gold gold)throws Exception;
    public Integer updateGold(Gold gold)throws Exception;
    public Integer deleteGoldById(Integer id)throws Exception;
}
