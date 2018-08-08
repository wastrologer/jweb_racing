package com.dao.mapper;

import com.pojo.Region;

import java.util.List;

public interface RegionMapper {
    public List<Region> getRegionByCondition(Region region)throws Exception;
    public Integer countRegionByCondition(Region region)throws Exception;
    public Integer addRegion(Region region)throws Exception;
    public Integer updateRegion(Region region)throws Exception;
    public Integer deleteRegionById(Integer id)throws Exception;
}
