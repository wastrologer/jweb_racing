package com.dao.mapper;

import com.pojo.Concern;

import java.util.List;

public interface ConcernMapper {
    public List<Concern> getConcernByCondition(Concern concern)throws Exception;
    public Integer countConcernByCondition(Concern concern)throws Exception;
    public Integer addConcern(Concern concern)throws Exception;
    public Integer updateConcern(Concern concern)throws Exception;
    public Integer deleteConcernById(Integer Id)throws Exception;
}
