package com.controller;

import com.common.cache.CacheClient;
import com.common.utils.SvcUtils;
import com.pojo.Collection;
import com.pojo.User;
import com.pojo.UserToken;
import com.service.ICollectionSvc;
import com.service.IEssaySvc;
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
@RequestMapping("/collection")
public class CollectionController extends BaseController {
    protected static final Logger logger = LoggerFactory.getLogger(CollectionController.class);

    @Resource
    private IUserSvc userSvcImpl;

    @Resource
    private SvcUtils svcUtils;
    @Resource
    private CacheClient cacheClient;
    @Resource
    private IEssaySvc essaySvcImpl;
    @Resource
    private ICollectionSvc collectionSvcImpl;

    /*addCollection*/
    @RequestMapping("/customer/collectEssay")
    @ResponseBody
    public Map<String, Object> collectEssay(Integer essayId){
        try {
            UserToken uk=getUserToken();
            if(uk!=null){
                User user= userSvcImpl.getUserById((int)uk.getUserId());
                if(user!=null){
                    Boolean collected=svcUtils.judgeCollected(user.getUserId(),essayId);
                    Integer res;
                    if(collected){
                        res=collectionSvcImpl.deleteCollectionByEssayId(user.getUserId(),essayId);
                    }else {
                        res=collectionSvcImpl.addCollection(user,essayId);
                    }
                    if(res==1){
                        return getStrMap("collected",!collected);
                    }
                    Map<String, Object> map = getErrorMap("收藏文章失败");
                    return map;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error",e);
            Map<String, Object> map = getErrorMap(e.getClass().getName());
            return map;
        }
        Map<String, Object> map =getUnlogedErrorMap();
        return map;
    }

    /*addCollection*/
    @RequestMapping("/customer/deleteCollection")
    @ResponseBody
    public Map<String, Object> deleteCollection(@RequestParam(value="collectionId", required=true)Integer collectionId){
        try {
            UserToken uk=getUserToken();
            if(uk!=null){
                User user= userSvcImpl.getUserById((int)uk.getUserId());
                if(user!=null){
                    Collection c=new Collection();
                    c.setCollectionId(collectionId);
                    Integer res=collectionSvcImpl.deleteCollectionByCondition(user,c);
                    if(res==1){
                        Map<String, Object> map = getSuccessMap();
                        return map;
                    }
                    Map<String, Object> map = getErrorMap("取消收藏失败");
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

}
