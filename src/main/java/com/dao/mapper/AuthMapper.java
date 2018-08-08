package com.dao.mapper;

import com.pojo.Auth;

import java.util.List;

public interface AuthMapper {
//    public Auth getAuthByAuthId(String id)throws Exception;
    public Auth getAuthByUserName(String name)throws Exception;
    public List<Auth> getAuthByAccurateCondition(Auth auth)throws Exception;

}
