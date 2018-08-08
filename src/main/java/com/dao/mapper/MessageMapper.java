package com.dao.mapper;

import com.pojo.Message;

import java.util.List;

public interface MessageMapper {
    public List<Message> getMessageByCondition(Message message)throws Exception;
    public Integer countMessageByCondition(Message message)throws Exception;
    public Integer addMessage(Message message)throws Exception;
    public Integer updateMessage(Message message)throws Exception;
    public Integer deleteMessageById(Integer id)throws Exception;
}
