package com.service;

import com.github.pagehelper.PageInfo;
import com.pojo.Account;
import com.pojo.Auth;
import com.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface IUserSvc {
    public Account getAccountByUserId(Integer userId)throws Exception;
    User selectUser(Integer id)throws Exception;
    public void addConcerned(Integer userFromId,List<User> users)throws Exception;
    //count
    public Integer countAllUser()throws Exception;
    public Integer countUserByRegionName(String name)throws Exception;
    public Integer countUserByAuthName(String name)throws Exception;
    public Integer countUserByAuthType(String type)throws Exception;
    public Integer countUserByConcernFrom(Integer from_id)throws Exception;
    public Integer countUserByConcernTo(Integer to_id)throws Exception;
    //select list by default page
    public PageInfo getAllUserByPage(Integer pageNum)throws Exception;
    public PageInfo getUserByRegionNameAndPage(String name, Integer pageNum)throws Exception;
    public PageInfo getUserByAuthNameAndPage(String name,Integer pageNum)throws Exception;
    public PageInfo getUserByAuthTypeAndPage(String type,Integer pageNum)throws Exception;
    public PageInfo getUserByConcernFromAndPage(Integer from_id,Integer pageNum)throws Exception;
    public PageInfo getUserByConcernToAndPage(Integer to_id,Integer pageNum)throws Exception;
    public PageInfo getUserByFuzzyConditionAndPage(User user,Integer pageNum)throws Exception;
    //select list by specific page
    public PageInfo getAllUserByPage(Integer pageNum,Integer pageSize)throws Exception;
    public PageInfo getUserByRegionNameAndPage(String name,Integer pageNum,Integer pageSize)throws Exception;
    public PageInfo getUserByAuthNameAndPage(String name,Integer pageNum,Integer pageSize)throws Exception;
    public PageInfo getUserByAuthTypeAndPage(String type,Integer pageNum,Integer pageSize)throws Exception;
    public PageInfo getUserByConcernFromAndPage(Integer from_id,Integer pageNum,Integer pageSize)throws Exception;
    public PageInfo getUserByConcernToAndPage(Integer to_id,Integer pageNum,Integer pageSize)throws Exception;
    public PageInfo getUserByFuzzyConditionAndPage(User user,Integer pageNum,Integer pageSize)throws Exception;
    public PageInfo getUserOrderByFans(Integer pageNum,Integer pageSize)throws Exception;
    public List<User> getUserByIds(Set<Integer> userIds)throws Exception;


    //select one
    public User getUserById(Integer id)throws Exception;
    public User getUserByPhoneNumber(String num)throws Exception;
    public User getUserByUserName(String name)throws Exception;
    public User getUserByInviteCode(String code)throws Exception;
    public User getUserById(Integer mine,Integer id)throws Exception;
    public User getUserByPhoneNumber(Integer mine,String num)throws Exception;
    public User getUserByUserName(Integer mine,String name)throws Exception;
    public User getUserByInviteCode(Integer mine,String code)throws Exception;
    public User getUserByToken(String token)throws Exception;
    public User getUserByAccurateCondition(User user)throws Exception;
    public User getUserByAccurateCondition(Integer mine,User user)throws Exception ;

    public Auth getAuthByAuthId(String name)throws Exception;
    public Auth getAuthByAuthName(String name)throws Exception;
    public Auth getAuthByUserName(String name)throws Exception;
    public Auth getAuthByAccurateCondition(Auth auth)throws Exception;

    //is exist
    public boolean isExistById(Integer id)throws Exception;
    public boolean isExistByPhoneNumber(String num)throws Exception;
    public boolean isExistByUserName(String name)throws Exception;
    public boolean isExistByInviteCode(String code)throws Exception;
    //conform to standard
    public boolean conformToPhoneNumber(String num)throws Exception;
    public boolean conformToUserName(String name)throws Exception;
    //write
    public int addGold(Integer num,Integer userId,String reason,Integer waterType,Long retId)throws Exception;
    public int addCoin(Integer num,Integer userId,String reason)throws Exception;
    public Integer addUser(User user)throws Exception;
    public Integer updateUserWithCoinAndGold(User user)throws Exception;
    public Integer updateUserOnly(User user)throws Exception;
    public Integer deleteUserById(Integer id)throws Exception;
    //
    public String generalToken(String userName, String platform, String signType)throws Exception;

}
