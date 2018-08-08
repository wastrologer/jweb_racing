package com.controller;

import com.common.cache.CacheClient;
import com.common.entity.Constants;
import com.common.utils.SvcUtils;
import com.constant.ErrorCode;
import com.github.pagehelper.PageInfo;
import com.pojo.Application;
import com.pojo.User;
import com.pojo.UserToken;
import com.service.IApplicationSvc;
import com.service.IUserSvc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/application")
public class ApplicationController extends BaseController {
    protected static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

    @Resource
    private IUserSvc userSvcImpl;
    @Resource
    private SvcUtils svcUtils;
    @Resource
    private CacheClient cacheClient;
    @Resource
    private IApplicationSvc applicationSvcImpl;

    /*getApplications*/
    @RequestMapping("/customer/getApplications")
    @ResponseBody
    public Map<String, Object> getApplications(@RequestParam(value="num", required=false)Integer num,
                                             @RequestParam(value="size", required=false)Integer size){
        try {
            UserToken uk=getUserToken();
            if(uk!=null){
                User user= userSvcImpl.getUserById((int)uk.getUserId());
                if(user!=null){
                    Application pa=new Application();
                    pa.setUserId(user.getUserId());
                    PageInfo pageInfo=applicationSvcImpl.getApplicationByConditionAndPage(pa,num,size);
                    return getStrMap(pageInfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> map = getErrorMap(e.getClass().getName());
            logger.error("error",e);
            return map;
        }
        Map<String, Object> map = getUnlogedErrorMap();
        return map;
    }
    /*applyCash*/
    @RequestMapping("/customer/applyCash")
    @ResponseBody
    public Map<String, Object> applyCash(@RequestParam(value="goldNum", required=true)Integer goldNum,
                                         @RequestParam(value="phoneNumber", required=true)String phoneNumber,
                                         @RequestParam(value="alipayAccount", required=true)String alipayAccount,
                                         @RequestParam(value="alipayName", required=true)String alipayName){
        try {
            UserToken uk=getUserToken();
            if(uk!=null){
                User user= userSvcImpl.getUserById((int)uk.getUserId());
                if(user!=null){
                    if(!user.getAuthId().equals(Constants.IDENTIFICATION_AUTH_ID)){
                        return getErrorMap(ErrorCode.APPLICATION_AUTH_NOT_IDENTIFY,"用户不是认证用户");
                    }
                    int result=-1;
                    String reason="金币余额不足或提现金币小于50";
                    if(goldNum<=user.getUserGold()&&goldNum>=50){
                        result=applicationSvcImpl.addApplicationWithAccountAndGold(user.getUserId(),goldNum,alipayAccount,alipayName,phoneNumber);
                        if(result==1){
                            reason="申请成功";
                            return getStrMap("result",result,"reason",reason);
                        } else
                            reason="操作失败";
                    }
                    return getErrorMap(ErrorCode.APPLICATION_GOLD_NOT_ENOUGH,reason);
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

    /*cancelApplication*/
    @RequestMapping("/customer/cancelApplication")
    @ResponseBody
    public Map<String, Object> cancelApplication(@RequestParam(value="applicationId", required=true)Integer applicationId){
        try {
            UserToken uk=getUserToken();
            if(uk!=null){
                User user= userSvcImpl.getUserById((int)uk.getUserId());
                if(user!=null){
                    int result;
                    String reason;
                    result=applicationSvcImpl.updateApplicationWithGoldAndUser(applicationId,user.getUserId(),2,"本人取消提现");
                    if(result==1)
                        reason="取消成功";
                    else
                        reason="操作失败";
                    return getStrMap("result",result,"reason",reason);
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
