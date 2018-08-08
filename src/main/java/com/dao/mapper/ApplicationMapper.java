package com.dao.mapper;

import com.pojo.Application;

import java.util.List;

public interface ApplicationMapper {
    public List<Application> getApplicationByCondition(Application application)throws Exception;
    public Integer countApplicationByCondition(Application application)throws Exception;
    public Integer addApplication(Application application)throws Exception;
    public Integer updateApplication(Application application)throws Exception;
    public Integer deleteApplicationById(Integer id)throws Exception;

    public  Integer getTotalWithdrawByUserId(Integer userId);
}
