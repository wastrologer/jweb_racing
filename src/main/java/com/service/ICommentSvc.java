package com.service;

import com.common.entity.ReturnMessage;
import com.github.pagehelper.PageInfo;
import com.pojo.Comment;
import com.pojo.User;

public interface ICommentSvc {
    public Integer countCommentByCondition(Comment comment)throws Exception;
    public Comment getCommentByAccurateCondition(Comment comment)throws Exception;
    public PageInfo getCommentByConditionAndPage(Comment comment, Integer pageNum, Integer pageSize)throws Exception;
    public PageInfo getSeniorCommentByEssayIdAndPage(Integer id, Integer pageNum, Integer pageSize)throws Exception;
    public PageInfo getJuniorCommentByCommentIdAndPage(Integer id, Integer pageNum, Integer pageSize)throws Exception;

    public PageInfo getCommentByConditionAndPage(Comment comment, Integer pageNum)throws Exception;
    public Integer addComment(User user, Comment comment)throws Exception;
    public Integer updateComment(User user,Comment comment)throws Exception;
    public Integer deleteCommentById(User user,Integer id)throws Exception;
    public Integer addComment(Comment comment)throws Exception;
    public Integer updateComment(Comment comment)throws Exception;
    public Integer updateCommentRecommendNum(ReturnMessage msg)throws Exception;

    public Integer updateCommentBatch(User user)throws Exception;
    public Integer deleteCommentById(Integer id)throws Exception;
}
