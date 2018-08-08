package com.service.impl;

import com.common.cache.CacheClient;
import com.common.entity.Constants;
import com.common.entity.SentenceConstants;
import com.common.utils.*;
import com.dao.mapper.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pojo.*;
import com.service.ICommentSvc;
import com.service.IEssaySvc;
import com.service.IUserSvc;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class UserSvcImpl implements IUserSvc {
    protected static final Logger logger = LoggerFactory.getLogger(UserSvcImpl.class);

    private Integer defaultPageSize=1000;

    private Integer defaultMine=0;


    @Resource
    private CacheClient cacheClient;

    @Value("${user.web.auth.valid.time}")
    private Integer webAuthValidTime = 30;

    @Value("${user.app.auth.valid.time}")
    private Integer appAuthValidTime = 30 * 24 * 60;
    @Resource
    UserMapper userMapper;
    @Resource
    AuthMapper authMapper;
    @Resource
    GoldMapper goldMapper;
    @Resource
    CoinMapper coinMapper;
    @Resource
    AccountMapper accountMapper;
    @Resource
    IEssaySvc essaySvcImpl;
    @Resource
    ICommentSvc commentSvcImpl;
    @Resource
    SvcUtils svcUtils;

    public Account getAccountByUserId(Integer userId)throws Exception {
        Account account=new Account();
        account.setUserId(userId);
        List<Account> result =accountMapper.getAccountByCondition(account);
        if(result.size()!=1){
            logger.info(account.toString());
        }
        return svcUtils.judgeResultList(result);
    }



    public void setUserGoldAndCoin(Account account,User user)throws Exception {
        user.setUserGold(account.getAllowWithdrawal());
        user.setUserCoin(account.getAllowCoin());
    }

    @Override
    public User selectUser(Integer id)throws Exception {
        return userMapper.selectUser(id);
    }

    public void addConcerned(Integer userFromId,List<User> users)throws Exception {
        for(User user:users){
            user.setConcerned(svcUtils.judgeConcerned(userFromId,user.getUserId()));
        }
    }
    @Override
    public PageInfo getAllUserByPage(Integer pageNum, Integer pageSize)throws Exception {
        if(pageNum==null)
            pageNum=1;
        if(pageSize==null||pageSize<=0)
            pageSize=defaultPageSize;
        PageHelper.startPage(pageNum,pageSize);
        List<User> userList=userMapper.getAllUser();
        UserInfoUtil.concealUserInfo(userList);
        PageInfo pageInfo=new PageInfo(userList);
        return pageInfo;
    }

    @Override
    public PageInfo getUserByRegionNameAndPage(String name, Integer pageNum, Integer pageSize)throws Exception {
        if(pageNum==null)
            pageNum=1;
        if(pageSize==null||pageSize<=0)
            pageSize=defaultPageSize;
        PageHelper.startPage(pageNum,pageSize);
        List<User> userList=userMapper.getUserByRegionName(name);
        UserInfoUtil.concealUserInfo(userList);
        PageInfo pageInfo=new PageInfo(userList);
        return pageInfo;
    }

    @Override
    public PageInfo getUserByAuthNameAndPage(String name, Integer pageNum, Integer pageSize)throws Exception {
        if(pageNum==null)
            pageNum=1;
        if(pageSize==null||pageSize<=0)
            pageSize=defaultPageSize;
        PageHelper.startPage(pageNum,pageSize);
        List<User> userList=userMapper.getUserByAuthName(name);
        UserInfoUtil.concealUserInfo(userList);
        PageInfo pageInfo=new PageInfo(userList);
        return pageInfo;
    }

    @Override
    public PageInfo getUserByAuthTypeAndPage(String type, Integer pageNum, Integer pageSize)throws Exception {
        if(pageNum==null)
            pageNum=1;
        if(pageSize==null||pageSize<=0)
            pageSize=defaultPageSize;
        PageHelper.startPage(pageNum,pageSize);
        List<User> userList=userMapper.getUserByAuthType(type);
        UserInfoUtil.concealUserInfo(userList);
        PageInfo pageInfo=new PageInfo(userList);
        return pageInfo;
    }

    @Override
    public PageInfo getUserByConcernFromAndPage(Integer from_id, Integer pageNum, Integer pageSize)throws Exception {
        if(pageNum==null)
            pageNum=1;
        if(pageSize==null||pageSize<=0)
            pageSize=defaultPageSize;
        PageHelper.startPage(pageNum,pageSize);
        List<User> userList=userMapper.getUserByConcernFrom(from_id);
        UserInfoUtil.concealUserInfo(userList);
        PageInfo pageInfo=new PageInfo(userList);
        return pageInfo;
    }

    @Override
    public PageInfo getUserByConcernToAndPage(Integer to_id, Integer pageNum, Integer pageSize)throws Exception {
        if(pageNum==null)
            pageNum=1;
        if(pageSize==null||pageSize<=0)
            pageSize=defaultPageSize;
        PageHelper.startPage(pageNum,pageSize);
        List<User> userList=userMapper.getUserByConcernTo(to_id);
        UserInfoUtil.concealUserInfo(userList);
        PageInfo pageInfo=new PageInfo(userList);
        return pageInfo;
    }

    @Override
    public PageInfo getUserByFuzzyConditionAndPage(User user, Integer pageNum, Integer pageSize)throws Exception {
        if(pageNum==null)
            pageNum=1;
        if(pageSize==null||pageSize<=0)
            pageSize=defaultPageSize;
        PageHelper.startPage(pageNum,pageSize);
        List<User> userList=userMapper.getUserByFuzzyCondition(user);
        UserInfoUtil.concealUserInfo(userList);
        PageInfo pageInfo=new PageInfo(userList);
        return pageInfo;
    }

    @Override
    public PageInfo getUserOrderByFans(Integer pageNum, Integer pageSize)throws Exception {
        if(pageNum==null)
            pageNum=1;
        if(pageSize==null||pageSize<=0)
            pageSize=defaultPageSize;
        PageHelper.startPage(pageNum,pageSize);
        List<User> userList=userMapper.getUserOrderByFans();
        UserInfoUtil.concealUserInfo(userList);
        PageInfo pageInfo=new PageInfo(userList);
        return pageInfo;
    }

    @Override
    public List<User> getUserByIds(Set<Integer> userIds)throws Exception {
        if(userIds!=null&&userIds.size()>0){
            return userMapper.getUserByIds(userIds);
        }else
            throw new Exception(userIds.toString());
    }

    @Override
    public PageInfo getAllUserByPage(Integer pageNum)throws Exception {
        return getAllUserByPage(pageNum,defaultPageSize);
    }

    @Override
    public PageInfo getUserByRegionNameAndPage(String name, Integer pageNum)throws Exception {
        return getUserByRegionNameAndPage(name,pageNum,defaultPageSize);
    }

    @Override
    public PageInfo getUserByAuthNameAndPage(String name, Integer pageNum)throws Exception {
        return getUserByAuthNameAndPage(name,pageNum,defaultPageSize);
    }

    @Override
    public PageInfo getUserByAuthTypeAndPage(String type, Integer pageNum)throws Exception {
        return getUserByAuthTypeAndPage(type,pageNum,defaultPageSize);
    }

    @Override
    public PageInfo getUserByConcernFromAndPage(Integer from_id, Integer pageNum)throws Exception {
        return getUserByConcernFromAndPage(from_id,pageNum,defaultPageSize);
    }

    @Override
    public PageInfo getUserByConcernToAndPage(Integer to_id, Integer pageNum)throws Exception {
        return getUserByConcernToAndPage(to_id,pageNum,defaultPageSize);
    }

    @Override
    public PageInfo getUserByFuzzyConditionAndPage(User user, Integer pageNum)throws Exception {
        return getUserByFuzzyConditionAndPage(user,pageNum,defaultPageSize);
    }

    @Override
    public User getUserById(Integer id)throws Exception {
        return getUserById(defaultMine,id);
    }

    @Override
    public User getUserByPhoneNumber(String num)throws Exception {
        return getUserByPhoneNumber(defaultMine,num);
    }

    @Override
    public User getUserByUserName(String name)throws Exception {
        return getUserByUserName(defaultMine,name);
    }

    @Override
    public User getUserByInviteCode(String code)throws Exception {
        return getUserByInviteCode(defaultMine,code);
    }

    @Override
    public User getUserById(Integer mine,Integer id)throws Exception {
        User user=new User();
        user.setUserId(id);
        return getUserByAccurateCondition(mine,user);
    }

    @Override
    public User getUserByPhoneNumber(Integer mine,String num)throws Exception {
        User user=new User();
        user.setPhoneNumber(num);
        return getUserByAccurateCondition(mine,user);
    }

    @Override
    public User getUserByUserName(Integer mine,String name)throws Exception {
        User user=new User();
        user.setUserName(name);
        return getUserByAccurateCondition(mine,user);
    }

    @Override
    public User getUserByInviteCode(Integer mine,String code)throws Exception {
        User user=new User();
        user.setInviteCode(code);
        return getUserByAccurateCondition( mine,user);
    }

    @Override
    public User getUserByToken(String token)throws Exception {
        return null;
    }

    @Override
    public User getUserByAccurateCondition(User user)throws Exception {
        return getUserByAccurateCondition(defaultMine,user);
    }

    @Override
    public User getUserByAccurateCondition(Integer mine,User user)throws Exception {
        List<User> result=userMapper.getUserByAccurateCondition(user);
        if(result.size()!=1){
            logger.info(user.toString());
        }
        if(mine!=1){
            UserInfoUtil.concealUserInfo(result);
        }
        for(User u:result){
            Account a=getAccountByUserId(u.getUserId());
            if(a!=null)
                setUserGoldAndCoin(a,u);
        }
        return svcUtils.judgeResultList(result);
    }

    @Override
    public Auth getAuthByAuthId(String id)throws Exception {
        Auth auth=new Auth();
        auth.setAuthId(id);
        return getAuthByAccurateCondition(auth);
    }


    @Override
    public Auth getAuthByUserName(String name)throws Exception {

        return authMapper.getAuthByUserName(name);
    }

    @Override
    public Auth getAuthByAuthName(String name)throws Exception {
        Auth auth=new Auth();
        auth.setAuthName(name);
        return getAuthByAccurateCondition(auth);
    }

    @Override
    public Auth getAuthByAccurateCondition(Auth auth)throws Exception {
        List<Auth> result=authMapper.getAuthByAccurateCondition(auth);
        if(result.size()!=1){
            logger.info(auth.toString());
        }
        return svcUtils.judgeResultList(result);
    }
    @Transactional
    @Override
    public Integer addUser(User user)throws Exception {
        //建立对象
        User newUser = new User();
        newUser.setPhoneNumber(user.getPhoneNumber());
        newUser.setUserName(user.getPhoneNumber());
        String password=MD5Util.md5(user.getPhoneNumber() + ":" + user.getPassword());
        newUser.setPassword(password);
        newUser.setRegionId(user.getRegionId());
        newUser.setUserPic(user.getUserPic());
        newUser.setBackdrop(user.getBackdrop());
        newUser.setCreateTime(new Timestamp(System.currentTimeMillis()));

        newUser.setAuthId("ROLE_USER");
        newUser.setUserCoin(0);
        newUser.setUserGold(0);
        newUser.setInviteNum(0);
        newUser.setFans(0);
        newUser.setConcern(0);



        newUser.setInviteCode(UserInfoUtil.generateInviteCode(6));
        if(StringUtil.isEmpty(user.getSignature())){
            String str= SentenceConstants.DEFAULT_SIGNATURE;
            newUser.setSignature(str);
        }else{
            newUser.setSignature(user.getSignature());
        }
        if(StringUtil.isEmpty(user.getNickname())){
            String str=StringUtil.getRandomString(11);//StringUtil.concealPhoneNumber(user.getPhoneNumber());
            newUser.setNickname(str);
        }else{
            newUser.setNickname(user.getNickname());
        }
        int i=userMapper.addUser(newUser);
        if(i!=1){
            throw new Exception(newUser.toString());
        }
        newUser=getUserByAccurateCondition(1,newUser);

        Account account=new Account();
        account.setUserId(newUser.getUserId());
        account.setCreateTime(new Timestamp(System.currentTimeMillis()));
        account.setTotalCoinAccount(0);
        account.setTotalGoldAccount(0);
        account.setAllowWithdrawal(0);
        account.setWithdrawalFreeze(0);

        int ii= accountMapper.addAccount(account);
        if(ii!=1){
            throw new Exception(account.toString());
        }
        return  ii;
/*            //使用经纬度和百度地图判定city
            if (null != latitude && null != longitude)throws Exception {
                user.setCity(BaiDuUtil.getCityByPosition(longitude, latitude));
            }*/
        //加入数据库
    }
    @Transactional
    @Override
    public Integer updateUserWithCoinAndGold(User user)throws Exception {
        //coin
        Account account=new Account();
        boolean added=false;
        if(user.getUserCoin()!=null){
            added=true;
            account.setAllowCoin(user.getUserCoin());
            if(user.getUserCoin()>0)
                account.setTotalCoinAccount(user.getUserCoin());
            addCoin(user.getUserCoin(),user.getUserId(), SentenceConstants.COIN_ADD_REASON_SYSTEM);
        }
        //gold暂时没有该功能
/*        if(user.getUserGold()!=null){
            account.setAllowWithdrawal(user.getUserGold());
            if(user.getUserGold()>0)
                account.setTotalGoldAccount(user.getUserGold());
            addGold(user.getUserGold(),user.getUserId(), SentenceConstants.GOLD_ADD_REASON_ESSAY);
        }*/
        //invite num
        if(user.getInviteNum()!=null){

            int add=Constants.INVITE_COIN_REWARD;
            if(added){
                account.setTotalCoinAccount(add+account.getTotalCoinAccount());
                account.setAllowCoin(add+account.getTotalCoinAccount());
            }else{
                account.setTotalCoinAccount(add);
                account.setAllowCoin(add);
            }
            addCoin(add,user.getUserId(), SentenceConstants.COIN_ADD_REASON_INVITE);
        }
        // concern
        //user.setConcern(user.getConcern());
        // fans
        //user.setFans(user.getFans());

        if(!StringUtil.isEmpty(user.getNickname())){
            Integer en=essaySvcImpl.updateEssayBatch(user);
            Integer cn=commentSvcImpl.updateCommentBatch(user);
            logger.info("Integer en=essaySvcImpl.updateEssayBatch(user)---数量---" +en+
                    "\nInteger cn=commentSvcImpl.updateCommentBatch(user)---数量---"+cn);
        }
        if(account.getAllowWithdrawal()!=null||account.getAllowCoin()!=null){
            account.setUserId(user.getUserId());
            int i=accountMapper.updateAccount(account);
            if(i!=1){
                throw new Exception(account.toString());
            }
        }
        int j= userMapper.updateUser(user);
        if(j!=1){
            throw new Exception(user.toString());
        }
        return  j;    }

    @Override
    public Integer updateUserOnly(User user)throws Exception {
        //coin
        Account account=new Account();
        boolean added=false;
        if(user.getUserCoin()!=null){
            added=true;
            account.setTotalCoinAccount(user.getUserCoin());
            //addCoin(user.getUserCoin(),user.getUserId(), SentenceConstants.COIN_ADD_REASON_SYSTEM);
        }
        //gold
        if(user.getUserGold()!=null){
            account.setTotalGoldAccount(user.getUserGold());
            //addGold(user.getUserGold(),user.getUserId(), SentenceConstants.GOLD_ADD_REASON_ESSAY);
        }
        //invite num
        if(user.getInviteNum()!=null){

            int add=Constants.INVITE_COIN_REWARD;
            if(added){
                account.setTotalCoinAccount(add+account.getTotalCoinAccount());
            }else{
                account.setTotalCoinAccount(add);
            }
            //addCoin(add,user.getUserId(), SentenceConstants.COIN_ADD_REASON_INVITE);
        }
        if(account.getTotalCoinAccount()!=null||account.getTotalGoldAccount()!=null){
            account.setUserId(user.getUserId());
            int i=accountMapper.updateAccount(account);
            if(i!=1){
                throw new Exception(account.toString());
            }
        }
        if(!StringUtil.isEmpty(user.getNickname())){
            Integer en=essaySvcImpl.updateEssayBatch(user);
            Integer cn=commentSvcImpl.updateCommentBatch(user);
            logger.info("Integer en=essaySvcImpl.updateEssayBatch(user)---数量---" +en+
                    "\nInteger cn=commentSvcImpl.updateCommentBatch(user)---数量---"+cn);
        }
        int k= userMapper.updateUser(user);
        if(k!=1){
            throw new Exception(user.toString());
        }
        return  k;
    }

    @Override
    public Integer deleteUserById(Integer id)throws Exception {
        int i= userMapper.deleteUserById(id);
        if(i!=1){
            throw new Exception("id="+id.toString());
        }
        return  i;
    }

    @Override
    public Integer countAllUser()throws Exception {
        return userMapper.countAllUser();
    }

    @Override
    public Integer countUserByRegionName(String name)throws Exception {
        return userMapper.countUserByRegionName(name);
    }

    @Override
    public Integer countUserByAuthName(String name)throws Exception {
        return userMapper.countUserByAuthName(name);
    }

    @Override
    public Integer countUserByAuthType(String type)throws Exception {
        return userMapper.countUserByAuthType(type);
    }

    @Override
    public Integer countUserByConcernFrom(Integer from_id)throws Exception {
        return userMapper.countUserByConcernFrom(from_id);
    }

    @Override
    public Integer countUserByConcernTo(Integer to_id)throws Exception {
        return userMapper.countUserByConcernTo(to_id);
    }

    @Override
    public boolean isExistById(Integer id)throws Exception {
        User user =getUserById(id);
        if (user==null)
            return false;
        else
            return true;
    }

    @Override
    public boolean isExistByPhoneNumber(String num)throws Exception {
        User user =getUserByPhoneNumber(num);
        if (user==null)
            return false;
        else
            return true;
    }

    @Override
    public boolean isExistByUserName(String name)throws Exception {
        User user =getUserByUserName(name);
        if (user==null)
            return false;
        else
            return true;
    }

    @Override
    public boolean isExistByInviteCode(String code)throws Exception {
        User user =getUserByInviteCode(code);
        if (user==null)
            return false;
        else
            return true;
    }

    @Override
    public boolean conformToPhoneNumber(String num)throws Exception {
        return false;
    }

    @Override
    public boolean conformToUserName(String name)throws Exception {
        return false;
    }



    public String generalToken(String userName, String platform, String signType) throws Exception  {
        User user = getUserByUserName(userName);
        if (user == null){
            throw new RuntimeException(userName + "user not found.");
        }
        String u_token = TokenUtil.generalToken(userName, RandomStringUtils.randomAlphanumeric(32),
                System.currentTimeMillis());
        String md5Key = MD5Util.md5(userName + "#" + u_token);
        String TOKEN_KEY = Constants.FORUM_U_TOKEN_KEY + md5Key;
        UserToken userToken = new UserToken();
        userToken.setUserId(user.getUserId());
        userToken.setToken(u_token);
        userToken.setSignType(signType);
        userToken.setAuthTime(new Date());

        userToken.setLastRefreshTime(System.currentTimeMillis());
        // 区分web，app登陆方式不同的过期时间策略
        Integer authValidTime = 30 * 60;
        authValidTime = appAuthValidTime * 60;
//		if (signType.equals("APP"))throws Exception {
////			authValidTime = appAuthValidTime * 60;
////		} else {
////			authValidTime = webAuthValidTime * 60;
////		}
        // cache30分钟过期
        boolean ret = cacheClient.set(TOKEN_KEY, authValidTime, userToken);
        if (!ret){
            // 重试一次
            ret = cacheClient.set(TOKEN_KEY, authValidTime, userToken);
        }
        if (ret) {
            return u_token;
        } else {
            throw new RuntimeException("u_token cache error");
        }
    }

    public int addCoin(Integer num,Integer userId,String reason)throws Exception {
        Coin coin=new Coin(new Timestamp(System.currentTimeMillis()),num,userId,reason);
        coin.setCreateTime(new Timestamp(System.currentTimeMillis()));
        int i= coinMapper.addCoin(coin);
        if(i!=1){
            throw new Exception(coin.toString());
        }
        return  i;
    }

    public int addGold(Integer num,Integer userId,String reason,Integer waterType,Long retId)throws Exception {
        Gold gold=new Gold(new Timestamp(System.currentTimeMillis()),num,userId,reason);
        gold.setCreateTime(new Timestamp(System.currentTimeMillis()));
        gold.setWaterType(waterType);
        gold.setRetId(retId);
        int i= goldMapper.addGold(gold);
        if(i!=1){
            throw new Exception(gold.toString());
        }
        return  i;
    }
    public CacheClient getCacheClient()throws Exception {
        return cacheClient;
    }

    public void setCacheClient(CacheClient cacheClient)throws Exception {
        this.cacheClient = cacheClient;
    }

    public Integer getWebAuthValidTime()throws Exception {
        return webAuthValidTime;
    }

    public void setWebAuthValidTime(Integer webAuthValidTime)throws Exception {
        this.webAuthValidTime = webAuthValidTime;
    }

    public Integer getAppAuthValidTime()throws Exception {
        return appAuthValidTime;
    }

    public void setAppAuthValidTime(Integer appAuthValidTime)throws Exception {
        this.appAuthValidTime = appAuthValidTime;
    }

    public AuthMapper getAuthMapper()throws Exception {
        return authMapper;
    }

    public void setAuthMapper(AuthMapper authMapper)throws Exception {
        this.authMapper = authMapper;
    }

    public UserMapper getUserMapper()throws Exception {
        return userMapper;
    }

    public void setUserMapper(UserMapper userMapper)throws Exception {
        this.userMapper = userMapper;
    }

    public Integer getDefaultPageSize()throws Exception {
        return defaultPageSize;
    }

    public void setDefaultPageSize(Integer defaultPageSize)throws Exception {
        this.defaultPageSize = defaultPageSize;
    }

    public static Logger getLogger()throws Exception {
        return logger;
    }

    public GoldMapper getGoldMapper()throws Exception {
        return goldMapper;
    }

    public void setGoldMapper(GoldMapper goldMapper)throws Exception {
        this.goldMapper = goldMapper;
    }

    public CoinMapper getCoinMapper()throws Exception {
        return coinMapper;
    }

    public void setCoinMapper(CoinMapper coinMapper)throws Exception {
        this.coinMapper = coinMapper;
    }

    public IEssaySvc getEssaySvcImpl()throws Exception {
        return essaySvcImpl;
    }

    public void setEssaySvcImpl(IEssaySvc essaySvcImpl)throws Exception {
        this.essaySvcImpl = essaySvcImpl;
    }

    public ICommentSvc getCommentSvcImpl()throws Exception {
        return commentSvcImpl;
    }

    public void setCommentSvcImpl(ICommentSvc commentSvcImpl)throws Exception {
        this.commentSvcImpl = commentSvcImpl;
    }
}
