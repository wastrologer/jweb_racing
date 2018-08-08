package com.service.impl;

import com.common.cache.CacheClient;
import com.common.utils.SvcUtils;
import com.dao.mapper.MessageMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pojo.Message;
import com.service.IMessageSvc;
import com.service.IUserSvc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

@Service
public class MessageSvcImpl implements IMessageSvc {
    private static final Logger logger = LoggerFactory.getLogger(MessageSvcImpl.class);
    private Integer defaultPageSize=99;

    @Resource
    private CacheClient cacheClient;

    @Resource
    MessageMapper messageMapper;
    @Resource
    SvcUtils svcUtils;

    @Resource
    IUserSvc userSvcImpl;

    @Override
    public Integer countMessageByCondition(Message message)throws Exception {
        return messageMapper.countMessageByCondition(message);
    }

    @Override
    public PageInfo getMessageByConditionAndPage(Message message, Integer pageNum, Integer pageSize)throws Exception {
        if(pageNum==null)
            pageNum=1;
        if(pageSize==null||pageSize<=0)
            pageSize=defaultPageSize;
        PageHelper.startPage(pageNum,pageSize);
        List<Message> messageList= messageMapper.getMessageByCondition(message);
        PageInfo pageInfo=new PageInfo(messageList);
        return pageInfo;
    }

    @Override
    public PageInfo getMessageByConditionAndPage(Message message, Integer pageNum)throws Exception {
        return getMessageByConditionAndPage(message,pageNum,defaultPageSize);
    }



    @Override
    public Integer addMessageOnly(Integer userId, Integer senderUserId,Integer messageType,Integer messageEssayId,String messageContent)throws Exception {
        Message message=new Message();
        message.setMessageUserId(userId);
        //message.(new Timestamp(System.currentTimeMillis()));
        message.setCreateTime(new Timestamp(System.currentTimeMillis()));
        message.setSenderUserId(senderUserId);
        message.setIsRead(0);
        message.setMessageContent(messageContent);
        message.setMessageType(messageType);
        message.setMessageEssayId(messageEssayId);

        int cNum= messageMapper.addMessage(message);
        if(cNum!=1){
            throw new Exception(message.toString());
        }
        logger.info("messageMapper.addMessage结果为:"+cNum);
        return cNum;
    }



    @Override
    public Message getMessageByAccurateCondition(Message message)throws Exception {
        List<Message> result= messageMapper.getMessageByCondition(message);
        if(result.size()!=1){
            logger.info(message.toString());
        }
        return svcUtils.judgeResultList(result);
    }
    @Override
    public Message getMessageById(Integer id)throws Exception {
        Message pa=new Message();
        pa.setMessageId(id);
        List<Message> result= messageMapper.getMessageByCondition(pa);
        if(result.size()!=1){
            throw new Exception("id="+id.toString());
        }
        return svcUtils.judgeResultList(result);
    }

    @Override
    public Integer makeMessageIsReadInDb(Integer messageId)throws Exception {
        Message message=new Message();
        message.setMessageId(messageId);
        message.setIsRead(1);

        int i= messageMapper.updateMessage(message);
        if(i!=1){
            throw new Exception(message.toString());
        }
        return  i;
    }
}
