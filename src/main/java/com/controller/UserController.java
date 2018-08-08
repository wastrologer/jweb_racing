package com.controller;

import com.common.cache.CacheClient;
import com.common.entity.CommonEnum;
import com.common.utils.MD5Util;
import com.common.utils.StringUtil;
import com.common.utils.SvcUtils;
import com.constant.ErrorCode;
import com.github.pagehelper.PageInfo;
import com.pojo.*;
import com.service.IAuthAuditSvc;
import com.service.IEssaySvc;
import com.service.IMessageSvc;
import com.service.IUserSvc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    protected static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private IUserSvc userSvcImpl;

    @Resource
    private SvcUtils svcUtils;
    @Resource
    private CacheClient cacheClient;
    @Resource
    private IEssaySvc essaySvcImpl;
    @Resource
    private IMessageSvc messageSvcImpl;
    
    @Resource
    private IAuthAuditSvc authAuditSvcImpl;

    @RequestMapping("/test")
    @ResponseBody
    public Map<String, Object> userInfo(@RequestParam(value="num", required=false)Integer num,
                                        @RequestParam(value="size", required=false)Integer size){
        try {
            System.out.println(userSvcImpl.getUserByAuthNameAndPage("用户",num,size).getList()+"======"+userSvcImpl.selectUser(1).getUserName());
            Essay e=new Essay();
            e.setRecommendNum(1846);
            e.setEssayContent("Maven构建一个最简单的Spring Boot + Spring MVC项目\n" +
                    "阅读量：8121");
            e.setEssayTitle("新闻");
            e.setEssayType(0);
            e.setRegionId(1);
            e.setPublishTime(new Timestamp(System.currentTimeMillis()));
            e.setUserId(1);
            return null;
        } catch (Exception e1) {
            e1.printStackTrace();
            Map<String, Object> map = getErrorMap(e1.getClass().getName());
            return map;
        }
    }
    /*管理员查看user信息*/
    @RequestMapping("/manager/userInfo")
    @ResponseBody
    public Map<String, Object> getUser(@RequestParam(value="userName", required=false)String userName,
                                       @RequestParam(value="phoneNumber", required=false)String phoneNumber,
                                       @RequestParam(value="inviteCode", required=false)String inviteCode){
        try {
            UserToken uk=getUserToken();
            if(uk!=null){
                User user= userSvcImpl.getUserById((int)uk.getUserId());
                if(user!=null){
                    Map<String, Object> map = getStrMap("user",user);
                    return map;
                }
            }else if(phoneNumber!=null&&!phoneNumber.equals("")){
                User user=userSvcImpl.getUserByPhoneNumber(phoneNumber);
                if(user!=null){
                    Map<String, Object> map = getStrMap("user",user);
                    return map;
                }
            }else if(inviteCode!=null&&!inviteCode.equals("")){
                User user=userSvcImpl.getUserByInviteCode(inviteCode);
                if(user!=null){
                    Map<String, Object> map = getStrMap("user",user);
                    return map;
                }
            }
            Map<String, Object> map = getErrorMap();
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            Map<String, Object> map = getErrorMap(e.getClass().getName());
            return map;        }
    }

    /*
    查看我的关注/粉丝
*/
    @RequestMapping("/getAllPlatformContact")
    @ResponseBody
    public Map<String, Object> getAllPlatformContact(){
        try {
            List<PlatformContact> ps=svcUtils.getAllPlatformContact();
            return getStrMap(ps,ps.size());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            Map<String, Object> map = getErrorMap(e.getClass().getName());
            return map;
        }
    }

    /*名人堂
    * 1首页上滑动的
2单独打开页面*/
    @RequestMapping("/getFamousUser")
    @ResponseBody
    public Map<String, Object> getFamousUser(@RequestParam(value="type", required=true)Integer type,
                                             @RequestParam(value="num", required=false)Integer num,
                                             @RequestParam(value="size", required=false)Integer size){
        try {
            UserToken uk=getUserToken();
            if(type!=null&&type==1){
                EssayParam param=svcUtils.getHotEssayParam(null,null,null);
                PageInfo<Essay> hotEssayPageInfo=essaySvcImpl.getEssayByConditionAndPage(param,1,999);
               List<Essay> hotEssays=hotEssayPageInfo.getList();
                Set<Integer> allUserIds=new HashSet<>();
                for(Essay e:hotEssays){
                    allUserIds.add(e.getUserId());
                }
                //List<User> hotUsers=userSvcImpl.getUserByIds(allUserIds);
                param.setCommentNumFrom(0);
                param.setCommentNumTo(50);
                PageInfo<Essay> normalEssayPageInfo=essaySvcImpl.getEssayByConditionAndPage(param,1,999);
                List<Essay> normalEssays=normalEssayPageInfo.getList();
                //Set<Integer> normalUserIds=new HashSet<>();
                for(Essay e:normalEssays){
                    allUserIds.add(e.getUserId());
                }
                List<User> allUsers=userSvcImpl.getUserByIds(allUserIds);
                //hotUsers.addAll(normalUsers);
                //List<User> userList=hotUsers;
                for(User u:allUsers){
                    if(uk!=null){
                        User user= userSvcImpl.getUserById((int)uk.getUserId());
                        u.setConcerned(svcUtils.judgeConcerned(user.getUserId(),u.getUserId()));
                    }else{
                        u.setConcerned(false);
                    }
                }
                return getStrMap(allUsers,allUsers.size());//一次性返回，不分页
            }else if(type!=null&&type==2){
                PageInfo<User> pageInfo=userSvcImpl.getUserOrderByFans(num,size);
                List<User> userList=pageInfo.getList();
                for(User u:userList){
                    if(uk!=null){
                        User user= userSvcImpl.getUserById((int)uk.getUserId());
                        u.setConcerned(svcUtils.judgeConcerned(user.getUserId(),u.getUserId()));
                    }else{
                        u.setConcerned(false);
                    }
                }
                return getStrMap(pageInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            Map<String, Object> map = getErrorMap(e.getClass().getName());
            return map;
        }
        return getErrorMap();
    }


    /*用户更新自己信息*/
    @RequestMapping("/customer/updateUserInfo")
    @ResponseBody
    public Map<String, Object> updateUserInfo(User u){
        UserToken uk=getUserToken();
        if(uk!=null){
            try {
                User myself= userSvcImpl.getUserById((int)uk.getUserId());
                User user= new User();
                user.setUserId(myself.getUserId());
                if(u.getNickname()!=null&&!u.getNickname().equals(myself.getNickname())){
                    User pu=new User();
                    pu.setNickname(u.getNickname());
                    User ru=userSvcImpl.getUserByAccurateCondition(pu);
                    if(ru!=null){
                        return getErrorMap(ErrorCode.USER_NICKNAME_EXIST_ERROR,"昵称重复");
                    }
                    user.setNickname(u.getNickname());
                    if(myself.getNicknameChanged()==2){
                        user.setNicknameChanged(1);
                    }
                }
                if(u.getBackdrop()!=null){
                    user.setBackdrop(u.getBackdrop());
                }
                if(u.getUserPic()!=null){
                    user.setUserPic(u.getUserPic());
                }
                if(u.getSignature()!=null){
                    user.setSignature(u.getSignature());
                }
                int i=userSvcImpl.updateUserOnly(user);
                if(i==1)
                    return getSuccessMap();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                e.printStackTrace();
                Map<String, Object> map = getErrorMap(e.getClass().getName());
                return map;
            }
        }
        Map<String, Object> map = getUnlogedErrorMap();
        return map;
    }

    /*用户查看详细信息，自己/他人*/
    @RequestMapping("/customer/info")
    @ResponseBody
    public Map<String, Object> getUserInfo(User u){
        UserToken uk=getUserToken();
        if(uk!=null){
            try {
                User myself= userSvcImpl.getUserById((int)uk.getUserId());
                User user= null;
                if(u.getUserId()==null&&u.getPhoneNumber()==null&&u.getInviteNum()==null){
                    user=myself;
                }else{
                    u.setUserName(null);
                    user = userSvcImpl.getUserByAccurateCondition(u);
                }
                //add concerned
                user.setConcerned(svcUtils.judgeConcerned(myself.getUserId(),user.getUserId()));
                if(user!=null){
                    Map<String, Object> map = getStrMap("user",user);
                    return map;
                }else{
                    return getErrorMap(ErrorCode.USER_NOT_EXIST,"找不到用户");
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                e.printStackTrace();
                Map<String, Object> map = getErrorMap(e.getClass().getName());
                return map;
            }
        }
        Map<String, Object> map = getUnlogedErrorMap();
        return map;
    }

    /*登陆之后cacheMsg*/
/*    @RequestMapping("/customer/cacheMsg")
    @ResponseBody
    public Map<String, Object> cacheMsg(){
        try {
            UserToken uk=getUserToken();
            if(uk!=null){
                User user= userSvcImpl.getUserById((int)uk.getUserId());
                if(user!=null){
                    Integer id=user.getUserId();
                    List<Message> list= svcUtils.getMessageByUserId(id);
                    svcUtils.cacheMsgByUserAndType(user,list,cacheClient);
                    return getSuccessMap();
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            Map<String, Object> map = getErrorMap(e.getClass().getName());
            return map;
        }
        Map<String, Object> map = getUnlogedErrorMap();
        return map;
    }*/
/*
    查看我的关注/粉丝
*/
    @RequestMapping("/customer/getConcernPerson")
    @ResponseBody
    public Map<String, Object> getConcernPerson(@RequestParam(value="type", required=true)String type,
                                                @RequestParam(value="num", required=false)Integer num,
                                                @RequestParam(value="size", required=false)Integer size){
        try {
            UserToken uk=getUserToken();
            if(uk!=null){
                User user= userSvcImpl.getUserById((int)uk.getUserId());
                if(user!=null){
                    if(!StringUtil.isEmpty(type)){
                        List<User> list=null;
                        PageInfo<User> pageInfo=null;
                        if(type.equals("fan")){
                            pageInfo=userSvcImpl.getUserByConcernToAndPage(user.getUserId(),num,size);
                        }else if(type.equals("concern")){
                            pageInfo=userSvcImpl.getUserByConcernFromAndPage(user.getUserId(),num,size);
                        }
                        for(User u: pageInfo.getList()){
                            u.setConcerned(svcUtils.judgeConcerned(user.getUserId(),u.getUserId()));
                            if(u.getConcerned()&& svcUtils.judgeConcerned(u.getUserId(),user.getUserId())){
                                u.setMutualConcerned(true);
                            }
                        }
                        return getStrMap(pageInfo);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            Map<String, Object> map = getErrorMap(e.getClass().getName());
            return map;
        }
        Map<String, Object> map = getUnlogedErrorMap();
        return map;
    }
/*
    关注用户或取消关注
*/
    @RequestMapping("/customer/concernPerson")
    @ResponseBody
    public Map<String, Object> concernPerson(@RequestParam(value="userId", required=true)Integer userId){
        try {
            UserToken uk=getUserToken();
            if(uk!=null){
                User user= userSvcImpl.getUserById((int)uk.getUserId());
                if(user!=null){
                    boolean concerned=svcUtils.judgeConcerned(user.getUserId(),userId);
                    Integer updateFromNum=-2;
                    Integer updateToNum=-2;
                    Integer addNum=-2;
                    if(!concerned){
                        addNum=svcUtils.addConcern(user.getUserId(),userId);
                        if (addNum==1) {
                            User pu=new User();
                            pu.setUserId(user.getUserId());
                            pu.setConcern(1);
                            updateFromNum = userSvcImpl.updateUserWithCoinAndGold(pu);
                            pu=new User();
                            pu.setUserId(userId);
                            pu.setFans(1);
                            updateToNum = userSvcImpl.updateUserWithCoinAndGold(pu);
                        }
                    }else{
                        addNum=svcUtils.deleteConcern(user.getUserId(),userId);
                        if (addNum!=null) {
                            User pu=new User();
                            pu.setUserId(user.getUserId());
                            pu.setConcern(-1);
                            updateFromNum = userSvcImpl.updateUserWithCoinAndGold(pu);
                            pu=new User();
                            pu.setUserId(userId);
                            pu.setFans(-1);
                            updateToNum = userSvcImpl.updateUserWithCoinAndGold(pu);
                        }
                    }
                    logger.info("---add----:"+addNum+"----\n----updateFromNum---:"+updateFromNum+"----updateToNum---:"+updateToNum);
                    if(addNum==1&&updateFromNum==1&&updateToNum==1){
                        if(concerned)
                            return getStrMap("concerned",!concerned);
                        else
                            return getStrMap("concerned",!concerned);
                    }else{
                        return getErrorMap("---add----:"+addNum+"----updateFromNum---:"+updateFromNum+"----updateToNum---:"+updateToNum);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            Map<String, Object> map = getErrorMap(e.getClass().getName());
            return map;
        }
        Map<String, Object> map = getUnlogedErrorMap();
        return map;
    }


    /*获取认证    */
    @RequestMapping("/customer/getIdentification")
    @ResponseBody
    public Map<String, Object> getIdentification(){
        try {
            UserToken uk=getUserToken();
            if(uk!=null){
                User user= userSvcImpl.getUserById(1,(int)uk.getUserId());
                if(user!=null){
                    return getStrMap("identification",svcUtils.getIdentificationByUser(user));
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            Map<String, Object> map = getErrorMap(e.getClass().getName());
            return map;
        }
        Map<String, Object> map = getUnlogedErrorMap();
        return map;
    }
    
    /*申请认证*/
    @RequestMapping("/customer/auditAuth")
    @ResponseBody
    public Map<String, Object> auditAuth(){
        try {
            UserToken uk=getUserToken();
            if(uk!=null){
                User user= userSvcImpl.getUserById(1,(int)uk.getUserId());
                if(user!=null){
                    AuthAudit authAudit = new AuthAudit();
                    authAudit.setUserId(user.getUserId().longValue());
                    authAudit.setApplicationTime(new Date());
                    authAudit.setCreateTime(new Date());
                    authAudit.setState(CommonEnum.AuthAuditState.AUDITING.value);
                    int result = authAuditSvcImpl.insertSelective(authAudit);
                    if (result <= 0 ) {
                    	 Map<String, Object> map = getErrorMap();
        				map.put("errorCode", ErrorCode.UPDATE_USER_DETAIL_ERROR);
        				map.put("msg", "申请认证失败");
        				return map;
        			}
                    return getSuccessMap();
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            Map<String, Object> map = getErrorMap(e.getClass().getName());
            return map;
        }
        Map<String, Object> map = getUnlogedErrorMap();
        return map;
    }
    
    /*changePassword    */
    @RequestMapping("/customer/changePassword")
    @ResponseBody
    public Map<String, Object> changePassword(@RequestParam(value="password", required=true)String password){
        try {
            UserToken uk=getUserToken();
            if(uk!=null){
                User user=userSvcImpl.getUserById(1,(int)uk.getUserId());
                User pu=new User();
                pu.setUserId((int)uk.getUserId());
                pu.setPassword(MD5Util.md5(user.getPhoneNumber() + ":" + password));
                Integer i= userSvcImpl.updateUserOnly(pu);
                if(i!=null&&i==1){
                    return getSuccessMap();
                }else{
                    return  getErrorMap("修改失败");
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            Map<String, Object> map = getErrorMap(e.getClass().getName());
            return map;
        }
        Map<String, Object> map = getUnlogedErrorMap();
        return map;
    }

    /*getSystemMessage    */
    @RequestMapping("/customer/getSystemMessage")
    @ResponseBody
    public Map<String, Object> getSystemMessage(@RequestParam(value="num", required=false)Integer num,
                                                @RequestParam(value="size", required=false)Integer size){
        try {
            UserToken uk=getUserToken();
            if(uk!=null){
                Message pm=new Message();
                //pm.setMessageUserId((int)uk.getUserId());
                pm.setMessageType(0);//系统消息
                PageInfo<Message>  pageInfo=messageSvcImpl.getMessageByConditionAndPage(pm,num,size);
                return getStrMap(pageInfo);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            Map<String, Object> map = getErrorMap(e.getClass().getName());
            return map;
        }
        Map<String, Object> map = getUnlogedErrorMap();
        return map;
    }
}
