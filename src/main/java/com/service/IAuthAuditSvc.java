package com.service;

import com.pojo.AuthAudit;

public interface IAuthAuditSvc {
	int deleteByPrimaryKey(Long id)throws Exception;

    int insert(AuthAudit record)throws Exception;

    int insertSelective(AuthAudit record)throws Exception;

    AuthAudit selectByPrimaryKey(Long id)throws Exception;

    int updateByPrimaryKeySelective(AuthAudit record)throws Exception;

    int updateByPrimaryKey(AuthAudit record)throws Exception;
}
