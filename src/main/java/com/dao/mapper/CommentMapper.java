package com.dao.mapper;

import com.pojo.Comment;
import com.pojo.User;

import java.util.List;

public interface CommentMapper {
    public List<Comment> getCommentByCondition(Comment comment)throws Exception;
    public Integer countCommentByCondition(Comment comment)throws Exception;
    public Integer addComment(Comment comment)throws Exception;
    public Integer updateComment(Comment comment)throws Exception;
    public Integer updateCommentBatch(User user)throws Exception;
    public Integer deleteCommentById(Integer id)throws Exception;

}
