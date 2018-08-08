package com.service;

import com.github.pagehelper.PageInfo;
import com.pojo.Message;

public interface IMessageSvc {
    public Integer countMessageByCondition(Message message)throws Exception;
    public Message getMessageByAccurateCondition(Message message)throws Exception;
    public Message getMessageById(Integer id)throws Exception ;

    public PageInfo getMessageByConditionAndPage(Message message, Integer pageNum, Integer pageSize)throws Exception;
    public PageInfo getMessageByConditionAndPage(Message message, Integer pageNum)throws Exception;

    public Integer addMessageOnly(Integer userId, Integer senderUserId,Integer messageType,Integer messageEssayId,String messageContent)throws Exception  ;
    public Integer makeMessageIsReadInDb(Integer messageId)throws Exception ;

}
