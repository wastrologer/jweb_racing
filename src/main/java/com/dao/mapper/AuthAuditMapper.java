package com.dao.mapper;

import java.util.List;

import com.pojo.AuthAudit;

public interface AuthAuditMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AuthAudit record);

    int insertSelective(AuthAudit record);

    AuthAudit selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AuthAudit record);

    int updateByPrimaryKey(AuthAudit record);

	List<AuthAudit> getAuthAudit(AuthAudit authAudit);
}