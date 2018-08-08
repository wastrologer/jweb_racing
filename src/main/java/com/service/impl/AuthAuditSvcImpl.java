package com.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dao.mapper.AuthAuditMapper;
import com.pojo.AuthAudit;
import com.service.IAuthAuditSvc;

@Service
public class AuthAuditSvcImpl implements IAuthAuditSvc {
	
	@Resource
	private AuthAuditMapper authAuditMapper;
	
	@Override
	public int deleteByPrimaryKey(Long id) throws Exception {
		return authAuditMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(AuthAudit record) throws Exception {
		return authAuditMapper.insert(record);
	}

	@Override
	public int insertSelective(AuthAudit record) throws Exception {
		return authAuditMapper.insertSelective(record);
	}

	@Override
	public AuthAudit selectByPrimaryKey(Long id) throws Exception {
		return authAuditMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(AuthAudit record) throws Exception {
		return authAuditMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(AuthAudit record) throws Exception {
		return authAuditMapper.updateByPrimaryKey(record);
	}

}
