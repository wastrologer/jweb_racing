package com.dao.mapper;

import com.pojo.Accusation;

import java.util.List;

public interface AccusationMapper {
    public List<Accusation> getAccusationByCondition(Accusation accusation) throws Exception;
    public Integer countAccusationByCondition(Accusation accusation) throws Exception;
    public Integer addAccusation(Accusation accusation) throws Exception;
    public Integer updateAccusation(Accusation accusation) throws Exception;
    public Integer deleteAccusationByCondition(Accusation accusation) throws Exception;
}
