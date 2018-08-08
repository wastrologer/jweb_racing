package com.dao.mapper;

import com.pojo.Account;

import java.util.List;

public interface AccountMapper {
    public List<Account> getAccountByCondition(Account account) throws Exception;
    public Integer countAccountByCondition(Account account) throws Exception;
    public Integer addAccount(Account account) throws Exception;
    public Integer updateAccount(Account account) throws Exception;
    public Integer deleteAccountById(Integer id) throws Exception;
}
