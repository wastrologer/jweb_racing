package com.dao.mapper;

import com.pojo.Collection;
import com.pojo.Essay;
import com.pojo.EssayParam;
import com.pojo.User;

import java.util.List;

public interface EssayMapper {
    public List<Essay> getEssayByCollectionCondition(Collection collection)throws Exception;
    public List<Essay> getEssayByUserList(List<Integer> list)throws Exception;
    public Essay getEssayComments()throws Exception;
    public Integer countEssayByCondition(Essay essay)throws Exception;
    public List<Essay> getEssayByAccurateCondition(Essay essay)throws Exception;
    public List<Essay> getEssayByFuzzyCondition(Essay essay)throws Exception;
    public List<Essay> getEssayByCondition(EssayParam essayParam)throws Exception;
    public List<Essay> getEssayOrderByTime(Essay essay)throws Exception;
    public List<Essay> getEssayOrderByClick(Essay essay)throws Exception;
    public Integer addEssay(Essay essay)throws Exception;
    public Integer updateEssay(Essay essay)throws Exception;
    public Integer updateEssayBatch(User user)throws Exception;
    public Integer deleteEssayById(Integer id)throws Exception;

}
