package com.dao.mapper;


import com.pojo.PlatformContact;

import java.util.List;

public interface PlatformContactMapper {
    int deleteByPrimaryKey(Long id)throws Exception;

    int insert(PlatformContact record)throws Exception;

    int insertSelective(PlatformContact record)throws Exception;

    PlatformContact selectByPrimaryKey(Long id)throws Exception;

    int updateByPrimaryKeySelective(PlatformContact record)throws Exception;

    int updateByPrimaryKey(PlatformContact record)throws Exception;

    public List<PlatformContact> selectAll()throws Exception;

}