package com.dao.mapper;

import com.pojo.Topic;

import java.util.List;

public interface TopicMapper {
    public List<Topic> getTopicByCondition(Topic topic)throws Exception;
    public Integer countTopicByCondition(Topic topic)throws Exception;
    public Integer addTopic(Topic topic)throws Exception;
    public Integer updateTopic(Topic topic)throws Exception;
    public Integer deleteTopicById(Integer id)throws Exception;
}
