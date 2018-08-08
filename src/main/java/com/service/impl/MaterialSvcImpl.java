package com.service.impl;

import com.pojo.Material;
import com.dao.mapper.MaterialMapper;
import com.service.IMaterialSvc;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

@Service
public class MaterialSvcImpl implements IMaterialSvc {
	
	@Resource
	private MaterialMapper materialDao;

	public List<Material> getMaterial(String materialId) throws Exception {
		Material m=new Material();
		m.setMaterialId(materialId);
		return materialDao.getMaterialByCondition(m);
	}

	public void insertMaterial(Material m) throws Exception {
		m.setCreateTime(new Timestamp(System.currentTimeMillis()));
		int i=materialDao.addMaterial(m);
		if(i!=1){
			throw new Exception(m.toString());
		}
	}
}
