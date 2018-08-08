package com.dao.mapper;


import com.pojo.Opinion;

public interface OpinionMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(Opinion record);

    Opinion selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Opinion record);

    int updateByPrimaryKey(Opinion record);
}