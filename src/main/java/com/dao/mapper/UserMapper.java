package com.dao.mapper;

import com.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;


public interface UserMapper {
    /*test*/
    public User selectUser(int id)throws Exception;
    //count
    public int countAllUser()throws Exception;
    public int countUserByRegionName(String name)throws Exception;
    public int countUserByAuthName(String name)throws Exception;
    public int countUserByAuthType(String type)throws Exception;
    public int countUserByConcernFrom(int from_id)throws Exception;
    public int countUserByConcernTo(int to_id)throws Exception;
    //select list
    public List<User> getAllUser()throws Exception;
    public List<User> getUserByRegionName(String name)throws Exception;
    public List<User> getUserByAuthName(String name)throws Exception;
    public List<User> getUserByAuthType(String type)throws Exception;
    public List<User> getUserByConcernFrom(int from_id)throws Exception;
    public List<User> getUserByConcernTo(int to_id)throws Exception;
    public List<User> getUserByFuzzyCondition(User user)throws Exception;
    public List<User> getUserByAccurateCondition(User user)throws Exception;
    public List<User> getUserOrderByFans()throws Exception;
    public List<User> getUserByIds(@Param("userIds")Set<Integer> userIds)throws Exception;
    //select one
/*
    public User getUserById(int id)throws Exception;
    public User getUserByPhoneNumber(String num)throws Exception;
    public User getUserByUserName(String name)throws Exception;
    public User getUserByInviteCode(String code)throws Exception;
*/

    //write
    public int addUser(User user)throws Exception;
    public int updateUser(User user)throws Exception;
    public int deleteUserById(int id)throws Exception;

}
