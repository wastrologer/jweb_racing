package com.controller;

import com.common.cache.CacheClient;
import com.common.utils.SvcUtils;
import com.constant.ErrorCode;
import com.github.pagehelper.PageInfo;
import com.pojo.Comment;
import com.pojo.Essay;
import com.pojo.User;
import com.pojo.UserToken;
import com.service.ICommentSvc;
import com.service.IEssaySvc;
import com.service.IUserSvc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController {
    protected static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Resource
    private IUserSvc userSvcImpl;
    @Resource
    private IEssaySvc essaySvcImpl;
    @Resource
    private SvcUtils svcUtils;
    @Resource
    private CacheClient cacheClient;
    @Resource
    private ICommentSvc commentSvcImpl;


    /*getCommentByEssayId*/
    @RequestMapping("/customer/getCommentByEssayId")
    @ResponseBody
    public Map<String, Object> getCommentByEssayId(@RequestParam(value="essayId", required=true)Integer essayId,
                                          @RequestParam(value="num", required=false)Integer num,
                                          @RequestParam(value="size", required=false)Integer size){
        try {
            UserToken uk=getUserToken();
            if(uk!=null){
                User user= userSvcImpl.getUserById((int)uk.getUserId());
                if(user!=null){
                    PageInfo<Comment> seniorPageInfo=commentSvcImpl.getSeniorCommentByEssayIdAndPage(essayId,num,size);
                    List<Comment> seniorList=seniorPageInfo.getList();
                    svcUtils.setCommentListRecommendedAndAuthId((int)uk.getUserId(),seniorList);
                    for(Comment c:seniorList){
                        c.setRecommended(svcUtils.judgeCommentRecommended(user.getUserId(),c.getCommentId()));
                        User su=userSvcImpl.getUserById(c.getUserId());
                        c.setUserPic(su.getUserPic());
                        PageInfo<Comment> juniorPageInfo=commentSvcImpl.getJuniorCommentByCommentIdAndPage(c.getCommentId(),
                                null,999);
                        svcUtils.setCommentListRecommendedAndAuthId((int)uk.getUserId(),juniorPageInfo.getList());
/*                        for(Comment jc:juniorPageInfo.getList()){
                            jc.setRecommended(svcUtils.judgeCommentRecommended(user.getUserId(),jc.getCommentId()));
                            User ju=userSvcImpl.getUserById(jc.getUserId());
                            jc.setUserPic(ju.getUserPic());
                        }*/
                        c.setJuniorComments(juniorPageInfo.getList());
                    }
                    return getStrMap(seniorPageInfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error",e);
            Map<String, Object> map = getErrorMap(e.getClass().getName());
            return map;
        }
        Map<String, Object> map = getUnlogedErrorMap();
        return map;
    }

    /*addComment*/
    @RequestMapping("/customer/addComment")
    @ResponseBody
    public Map<String, Object> addComment(Comment comment){
        try {
            UserToken uk=getUserToken();
            if(uk!=null){
                User user= userSvcImpl.getUserById((int)uk.getUserId());
                if(user!=null){
                    Integer res=commentSvcImpl.addComment(user,comment);
                    if(res==1){
                        Essay pe=new Essay();
                        pe.setEssayId(comment.getEssayId());
                        pe.setCommentNum(1);
                        Integer updateNum = essaySvcImpl.updateEssay(pe);
/*                        ReturnMessage msg=new ReturnMessage();
                        msg.setEssayId(comment.getEssayId());
                        msg.setAddCommentNum(1);
                        msgProducer.send(MsgCommandId.FORUM_ESSAY_COMMENTNUM_ID,msg);*/
                        //Integer updateNum = 1;
                        if(updateNum!=null&&updateNum==1)
                            return getSuccessMap();
                    }
                    Map<String, Object> map = getErrorMap("评论发布失败/消息添加失败");
                    return map;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error",e);
            Map<String, Object> map = getErrorMap(e.getClass().getName());
            return map;
        }
        Map<String, Object> map = getUnlogedErrorMap();
        return map;
    }


    @RequestMapping("/customer/recommendComment")
    @ResponseBody
    public Map<String, Object> recommendComment(
            @RequestParam(value="commentId", required=true)Integer commentId){
        try {
            UserToken uk=getUserToken();
            if(uk!=null){
                User user= userSvcImpl.getUserById((int)uk.getUserId());//43);
                if(user!=null){
                    Integer updateNum=-2;
                    Integer addNum=-2;
                    Boolean recommended=svcUtils.judgeCommentRecommended(user.getUserId(),commentId);
                    if(!recommended){
                        addNum=svcUtils.addRecommend(user.getUserId(),null,commentId);
                        if (addNum==1) {
                            Comment pc=new Comment();
                            pc.setCommentId(commentId);
                            pc.setRecommendNum(1);
                            updateNum = commentSvcImpl.updateComment(pc);
/*                            ReturnMessage msg=new ReturnMessage();
                            msg.setCommentId(commentId);
                            msg.setAddRecommendNum(1);
                            msgProducer.send(MsgCommandId.FORUM_COMMENT_RECOMMENDNUM_ID,msg);
                            updateNum = 1;*/
                        }
                    }else {
                        addNum=svcUtils.deleteRecommend(user.getUserId(),null,commentId);
                        if (addNum==1) {
                            Comment pc=new Comment();
                            pc.setCommentId(commentId);
                            pc.setRecommendNum(-1);
                            updateNum = commentSvcImpl.updateComment(pc);
/*                            ReturnMessage msg=new ReturnMessage();
                            msg.setCommentId(commentId);
                            msg.setAddRecommendNum(-1);
                            msgProducer.send(MsgCommandId.FORUM_COMMENT_RECOMMENDNUM_ID,msg);
                            updateNum = 1;*/
                        }
                    }
                    logger.info("recommendComment:\n-------------add----------:"+addNum+"\n---------update-------:"+updateNum);
                    if(addNum==1&&updateNum==1){
                        return getStrMap("recommended",!recommended);
                    }
                    return getErrorMap(ErrorCode.RECOMMEND_ERROR,"推荐操作失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error",e);
            Map<String, Object> map = getErrorMap(e.getClass().getName());
            return map;
        }
        Map<String, Object> map = getUnlogedErrorMap();
        return map;
    }

}
