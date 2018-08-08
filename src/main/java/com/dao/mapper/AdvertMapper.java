package com.dao.mapper;


import com.pojo.Advert;

import java.util.List;

public interface AdvertMapper {
    int deleteByPrimaryKey(Long advertId)throws Exception;

    int insert(Advert record)throws Exception;

    int insertSelective(Advert record)throws Exception;

    Advert selectByPrimaryKey(Long advertId)throws Exception;

    List<Advert> selectByPosition(Integer advertPosition)throws Exception;

    int updateByPrimaryKeySelective(Advert record)throws Exception;

    int updateByPrimaryKey(Advert record)throws Exception;
}