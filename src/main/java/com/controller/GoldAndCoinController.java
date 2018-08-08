package com.controller;

import com.common.cache.CacheClient;
import com.common.utils.SvcUtils;
import com.github.pagehelper.PageInfo;
import com.pojo.*;
import com.service.IApplicationSvc;
import com.service.IGoldAndCoinSvc;
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
@RequestMapping("/goldAndCoin")
public class GoldAndCoinController extends BaseController {
    protected static final Logger logger = LoggerFactory.getLogger(GoldAndCoinController.class);

    @Resource
    private IUserSvc userSvcImpl;
    @Resource
    private SvcUtils svcUtils;
    @Resource
    private CacheClient cacheClient;
    @Resource
    private IGoldAndCoinSvc goldAndCoinSvcImpl;
    @Resource
    private IApplicationSvc applicationSvcImpl;


    /*getGoldRecord*/
    @RequestMapping("/customer/getGoldRecord")
    @ResponseBody
    public Map<String, Object> getGoldRecord(@RequestParam(value="num", required=false)Integer num,
                                             @RequestParam(value="size", required=false)Integer size){
        try {
            UserToken uk=getUserToken();
            if(uk!=null){
                User user= userSvcImpl.getUserById((int)uk.getUserId());
                if(user!=null){
                    Account account=userSvcImpl.getAccountByUserId((int)uk.getUserId());
                    Integer totalWithdraw=applicationSvcImpl.getTotalWithdrawByUserId((int)uk.getUserId());
                    Gold pg=new Gold();
                    pg.setGoldUserId(user.getUserId());
                    PageInfo pageInfo=goldAndCoinSvcImpl.getGoldByConditionAndPage(pg,num,size);
                    return getStrMap(pageInfo.getList(), (int) pageInfo.getTotal(),"totalGold",user.getUserGold(),"freezeGold",account.getWithdrawalFreeze(),"totalWithdraw",totalWithdraw);
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

    /*getCoinRecord*/
    @RequestMapping("/customer/getCoinRecord")
    @ResponseBody
    public Map<String, Object> getCoinRecord(@RequestParam(value="num", required=false)Integer num,
                                             @RequestParam(value="size", required=false)Integer size){
        try {
            UserToken uk=getUserToken();
            if(uk!=null){
                User user= userSvcImpl.getUserById((int)uk.getUserId());
                if(user!=null){
                    Coin pc=new Coin();
                    pc.setCoinUserId(user.getUserId());
                    PageInfo pageInfo=goldAndCoinSvcImpl.getCoinByConditionAndPage(pc,num,size);
                    return getStrMap(pageInfo.getList(), (int) pageInfo.getTotal(),"totalCoin",user.getUserCoin());
                    //return getStrMap(pageInfo);
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

    /*spendGold*/
    @RequestMapping("/customer/spendGold")
    @ResponseBody
    public Map<String, Object> spendGold(@RequestParam(value="goldNum", required=true)Integer goldNum,
                                         @RequestParam(value="reason", required=true)String reason){
        try {
            UserToken uk=getUserToken();
            if(uk!=null){
                User user= userSvcImpl.getUserById((int)uk.getUserId());
                if(user!=null){
                    
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
