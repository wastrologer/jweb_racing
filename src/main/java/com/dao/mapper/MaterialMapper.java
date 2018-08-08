package com.dao.mapper;


import com.pojo.Material;

import java.util.List;

public interface MaterialMapper{
    public List<Material> getMaterialByCondition(Material material)throws Exception;
    public Integer countMaterialByCondition(Material material)throws Exception;
    public Integer addMaterial(Material material)throws Exception;
    public Integer updateMaterial(Material material)throws Exception;
    public Integer deleteMaterialById(Integer id)throws Exception;
}