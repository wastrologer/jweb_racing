package com.service;

import com.github.pagehelper.PageInfo;
import com.pojo.Application;

public interface IApplicationSvc {
    public Integer countApplicationByCondition(Application application)throws Exception;
    public Application getApplicationByAccurateCondition(Application application)throws Exception;
    public Application getApplicationById(Integer id)throws Exception ;

    public PageInfo getApplicationByConditionAndPage(Application application, Integer pageNum, Integer pageSize)throws Exception;

    public Integer getTotalWithdrawByUserId(Integer userId)throws Exception;

    public PageInfo getApplicationByConditionAndPage(Application application, Integer pageNum)throws Exception;
    public Integer addApplicationWithAccountAndGold(Integer userId, Integer goldNum,String alipayAccount,String alipayName,String phoneNumber) throws Exception;
    public Integer addApplicationOnly(Integer userId, Integer goldNum,String alipayAccount,String alipayName,String phoneNumber)throws Exception ;
    public Integer addApplicationWithUser(Integer userId, Integer goldNum,String alipayAccount,String alipayName,String phoneNumber)throws Exception;
    public Integer updateApplicationOnly(Integer applicationId,Integer auditorId,Integer applyState,String reason)throws Exception ;
    public Integer updateApplicationWithGoldAndUser(Integer applicationId,Integer auditorId,Integer applyState,String reason)throws Exception ;
    
}
