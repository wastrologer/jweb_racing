package com.controller;

import com.common.cache.CacheClient;
import com.common.utils.QrCodeUtil;
import com.common.utils.SvcUtils;
import com.constant.ErrorCode;
import com.pojo.Advert;
import com.pojo.User;
import com.pojo.UserToken;
import com.service.IUserSvc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/utils")
public class UtilsController extends BaseController {
    protected static final Logger logger = LoggerFactory.getLogger(UtilsController.class);

    @Resource
    private IUserSvc userSvcImpl;
    @Resource
    private SvcUtils svcUtils;
    @Resource
    private CacheClient cacheClient;
    @Resource
    private QrCodeUtil qrCodeUtil;

    @RequestMapping("/getAdvertByPosition")
    @ResponseBody
    public Map<String, Object> getAdvertByPosition(@RequestParam(value="position", required=false)Integer position,
                                                   @RequestParam(value="advertId", required=false)Long advertId){
        try {
            if(position==null){
                boolean b=false;
                b=svcUtils.clickAdvert(advertId);
                logger.info("svcUtils.browseAdvert(advert.getAdvertId())="+b);
                if(b)
                    return getSuccessMap();
                else
                    return getErrorMap(ErrorCode.STANDARD_ERROR_CODE,"点击失败");
            }

            Advert advert=svcUtils.getAdvertByPosition(position);
            boolean b=true;
            if(advert!=null){
                b=svcUtils.browseAdvert(advert.getAdvertId());
                logger.info("svcUtils.browseAdvert(advert.getAdvertId())="+b);
            }
            if(b)
                return getStrMap("advert",advert);
            else
                return getErrorMap(ErrorCode.STANDARD_ERROR_CODE,"浏览量增加失败");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            Map<String, Object> map = getErrorMap(e.getClass().getName());
            return map;
        }
    }

    /*getQrCode*/
    @RequestMapping("/customer/getQrCode")
    @ResponseBody
    public Map<String, Object> getQrCode(String url, Integer size,
                                         HttpServletRequest request, HttpServletResponse response){
        try {
            UserToken uk=getUserToken();
            if(uk!=null){
                User user= userSvcImpl.getUserById((int)uk.getUserId());
                request.setCharacterEncoding("utf-8");
                response.setContentType("text/html;charset=utf-8");

                // 设置响应的类型格式为图片格式
                response.setContentType("image/jpeg");
//			response.setHeader("content-type", "image/jpeg;charset=UTF-8");
                //禁止图像缓存。
                response.setHeader("Pragma", "No-cache");
                response.setHeader("Cache-Control", "no-cache");
                response.setDateHeader("Expires", 0);
                qrCodeUtil.createQrCode(size,url,response);
                return getSuccessMap();
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
