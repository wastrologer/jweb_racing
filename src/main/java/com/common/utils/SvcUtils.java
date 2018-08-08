package com.common.utils;

import com.common.entity.Constants;
import com.dao.mapper.*;
import com.pojo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class SvcUtils {

    protected static final Logger logger = LoggerFactory.getLogger(SvcUtils.class);
    @Resource
    PlatformContactMapper platformContactMapper;
    @Resource
    ConcernMapper concernMapper;

    @Resource
    EssayMapper essayMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    RegionMapper regionMapper;

/*    @Resource
    MessageMapper messageMapper;*/

    @Resource
    RecommendMapper recommendMapper;

    @Resource
    AccusationMapper accusationMapper;

    @Resource
    OpinionMapper opinionMapper;

    @Resource
    CollectionMapper collectionMapper;

    @Resource
    AdvertMapper advertMapper;
    
    @Resource
    private AuthAuditMapper authAuditMapper;

/*    @PostConstruct
    *//*在方法上加上注解@PostConstruct，这个方法就会在Bean初始化之后被Spring容器执行
    （注：Bean初始化包括，实例化Bean，并装配Bean的属性（依赖注入））。
        顺序上Constructor >> @Autowired >> @PostConstruct*//*
    public void init(){
        userMapper=userMapper1;
        concernMapper=concernMapper1;
        regionMapper=regionMapper1;
        messageMapper=messageMapper1;
    }*/

    /**
     * 通过位置信息获取广告
     * @param position
     * @return
     * @throws Exception
     */
    public Advert getAdvertByPosition(Integer position)throws Exception {
        List<Advert> adverts=advertMapper.selectByPosition(position);
        if(adverts.size()==0){
            return null;
        }else{
            return adverts.get(0);
        }
    }

    /**
     * 广告浏览量加1
     * @param id
     * @return
     * @throws Exception
     */
    public boolean browseAdvert(Long id)throws Exception {
        Advert advert=new Advert();
        advert.setAdvertId(id);
        advert.setBrowseNum(1L);
        int i=advertMapper.updateByPrimaryKeySelective(advert);
        if(i==1){
            return true;
        }else{
            throw new Exception(advert.toString());
        }
    }

    /**
     * 点击广告，点击量加1
     * @param id
     * @return
     * @throws Exception
     */
    public boolean clickAdvert(Long id)throws Exception {
        Advert advert=new Advert();
        advert.setAdvertId(id);
        advert.setClickNum(1L);
        int i=advertMapper.updateByPrimaryKeySelective(advert);
        if(i==1){
            return true;
        }else{
            throw new Exception(advert.toString());
        }
    }

    /**
     * 获取所有客服联系方式
     * @return
     * @throws Exception
     */
    public List<PlatformContact> getAllPlatformContact()throws Exception {
        return platformContactMapper.selectAll();
    }

    /**
     * 判断结果集是否符合逻辑，只有结果size唯一时符合
     * @param result
     * @param <T>
     * @return
     */
    public  <T> T judgeResultList(List<T> result){
        if(result.size()==1){
            return result.get(0);
        }else if(result.size()==0){
            return null;
        }else{
            throw new RuntimeException("查询到的对象数量为："+result.size()+".不合逻辑");
        }
    }

    public EssayParam getRecommendEssayParam(Integer cityId,Integer regionId,Integer type)throws Exception {
        EssayParam param=new EssayParam();
        param.setIsPublished(1);
        param.setRegionId(regionId);
        param.setCityId(cityId);
        param.setEssayType(type);
        param.setRecommendNumFrom(Constants.RECOMMEND_NUM_FROM);
        //param.setEssayTypeList(type);
        //param.setIsHot(true);
        param.setPublishTimeFrom(DateUtil.getSpecifiedDay(-2));
        param.setPublishTimeTo(new Date());
        param.setOrderBy(Constants.ORDER_BY_RECOMMEND_NUM);
        return param;
    }

    /**
     * 获取热门文章的查询用参数
     * @param cityId
     * @param regionId
     * @param type
     * @return
     * @throws Exception
     */
    public EssayParam getHotEssayParam(Integer cityId,Integer regionId,Integer type)throws Exception {
        EssayParam param=new EssayParam();
        param.setIsPublished(1);
        param.setRegionId(regionId);
        param.setCityId(cityId);
        param.setEssayType(type);
        param.setCommentNumFrom(Constants.HOT_COMMENT_NUM_FROM);
        //param.setEssayTypeList(type);
        param.setIsHot(true);
        param.setPublishTimeFrom(DateUtil.getSpecifiedDay(-2));
        param.setPublishTimeTo(new Date());
        param.setOrderBy(Constants.ORDER_BY_COMMENT_NUM);
        return param;
    }

    public  EssayParam getNormalEssayParam(Integer cityId,Integer regionId,Integer type)throws Exception {
        EssayParam param=new EssayParam();
        param.setIsPublished(1);
        param.setRegionId(regionId);
        param.setCityId(cityId);
        param.setEssayType(type);
        param.setOrderBy(null);
        return param;
    }

    public  List<Integer> getCityRegionListByRegionId(Integer regionId)throws Exception {
        if(regionId!=null){
            Region pr=new Region();
            pr.setRegionId(regionId);
            List<Region> rs=regionMapper.getRegionByCondition(pr);
            String city=rs.get(0).getCityName();
            pr=new Region();
            pr.setCityName(city);
            rs=regionMapper.getRegionByCondition(pr);
            List<Integer> list=new ArrayList<>();
            for(Region r:rs){
                list.add(r.getRegionId());
            }
            return  list;
        }else{
            throw new RuntimeException("regionId："+regionId+".不合逻辑");
        }

    }

    public  List<Integer> getRegionListByUserId(Integer id)throws Exception {
        User pu=new User();
        pu.setUserId(id);
        List<User> result=userMapper.getUserByAccurateCondition(pu);
        User u=judgeResultList(result);
        Integer regionId=null;
        if(u!=null)
            regionId= u.getRegionId();
        return  getCityRegionListByRegionId(regionId);

    }

    public  List<Integer> getAllRegionList()throws Exception {
        Region pr=new Region();
        List<Region> result=regionMapper.getRegionByCondition(pr);
        List<Integer> list=new ArrayList<>();
        for(Region r:result){
            list.add(r.getRegionId());
        }
        return  list;
    }

    public Integer addRecommend(Integer userId,Integer essayId,Integer commentId)throws Exception {
        Recommend rc=new Recommend();
        rc.setCommentId(commentId);
        rc.setEssayId(essayId);
        rc.setUserId(userId);
        rc.setCreateTime(new Timestamp(System.currentTimeMillis()));
        int i=  recommendMapper.addRecommend(rc);
        if(i!=1){
            throw  new Exception("addRecommend"+"失败"+rc.toString());
        }
        return i;
    }

    /**
     * 添加意见信息
     * @param userId
     * @param essayId
     * @param accusationContent
     * @return
     * @throws Exception
     */
    public Integer addAccusation(Integer userId,Integer essayId,String accusationContent)throws Exception {
        Accusation accusation=new Accusation();
        accusation.setAccusationContent(accusationContent);
        accusation.setEssayId(essayId);
        accusation.setUserId(userId);
        accusation.setCreateTime(new Timestamp(System.currentTimeMillis()));
        int i=  accusationMapper.addAccusation(accusation);
        if(i!=1){
            throw  new Exception("addAccusation"+"失败"+accusation.toString());
        }
        return i;
    }

    /**
     * 添加反馈信息
     * @param userId
     * @param content
     * @return
     * @throws Exception
     */
    public int addOpinion(long userId, String content)throws Exception  {
        Opinion opinion=new Opinion();
        opinion.setContent(content);
        opinion.setUserId(userId);
        opinion.setCreateTime(new Timestamp(System.currentTimeMillis()));
        int i=  opinionMapper.insertSelective(opinion);
        if(i!=1){
            throw  new Exception();
        }
        return i;
    }
    public Integer deleteRecommend(Integer userId,Integer essayId,Integer commentId)throws Exception {
        Recommend rc=new Recommend();
        rc.setCommentId(commentId);
        rc.setEssayId(essayId);
        rc.setUserId(userId);
        List<Recommend> rcs=recommendMapper.getRecommendByCondition(rc);
        if(rcs.size()!=1){
            throw  new Exception("recommendMapper.getRecommendByCondition(rc)"+"不唯一"+rc.toString());
        }
        int i=  recommendMapper.deleteRecommendById(rcs.get(0).getRecommendId());
        if(i!=1){
            throw  new Exception("deleteRecommendById"+"失败"+rcs.get(0).getRecommendId());
        }
        return i;
    }

    /**
     * 添加关注信息
     * @param userFromId
     * @param userToId
     * @return
     * @throws Exception
     */
    public Integer addConcern(Integer userFromId,Integer userToId)throws Exception {
        Concern c=new Concern();
        c.setUserFromId(userFromId);
        c.setUserToId(userToId);
        c.setCreateTime(new Timestamp(System.currentTimeMillis()));
        int i=  concernMapper.addConcern(c);
        if(i!=1){
            throw new Exception(c.toString());
        }
        return i;
    }

    /**
     * 删除关注信息
     * @param userFromId
     * @param userToId
     * @return
     * @throws Exception
     */
    public Integer deleteConcern(Integer userFromId,Integer userToId)throws Exception {
        Concern c=new Concern();
        c.setUserFromId(userFromId);
        c.setUserToId(userToId);
        List<Concern> cs=concernMapper.getConcernByCondition(c);
        if(cs.size()!=1){

            throw new Exception(c.toString());
            //return  cs.size();
        }
        int i=  concernMapper.deleteConcernById(cs.get(0).getConcernId());
        if(i!=1){
            throw new Exception("id="+cs.get(0).getConcernId());
        }
        return i;
    }

    public  List<Integer> getUserIdByConcern(Integer id)throws Exception {
        Concern c=new Concern();
        c.setUserFromId(id);
        List<Concern> cs=concernMapper.getConcernByCondition(c);
        List<Integer> ids=new ArrayList<Integer>();
        for (Concern con:cs){
            ids.add(con.getUserToId());
        }
        return ids;
    }
    public  Integer getIdByUserName(String s)throws Exception {
        User p=new User();
        p.setUserName(s);
        List<User> result=userMapper.getUserByAccurateCondition(p);
        User u=judgeResultList(result);
        if(u!=null){
            return result.get(0).getUserId();
        }else{
            return null;
        }
    }

/*    public  List<Message> getMessageByUserId(Integer id)throws Exception {
        Message pm=new Message();
        pm.setMessageUserId(id);
        List<Message> res=messageMapper.getMessageByCondition(pm);
        return res;
    }

    public  List<Message> getMessageByUserAndEssay(Integer userId,Integer essayId,Integer isRead)throws Exception {
        Message pm=new Message();
        pm.setMessageUserId(userId);
        pm.setMessageEssayId(essayId);
        pm.setIsRead(isRead);
        List<Message> res=messageMapper.getMessageByCondition(pm);
        return res;
    }

    public int makeMessageListIsRead(List<Message> list)throws Exception {
        int num=0;
        for(Message m:list){
            m.setIsRead(1);
            int i=messageMapper.updateMessage(m);
            if(i!=1){
                throw new Exception(m.toString());
            }
            num+=i;
        }
        return num;
    }

    public static HashMap<String,Message> getMapByMsgList(List<Message> list)throws Exception {
        HashMap<String,Message> map=new HashMap<>();
        for(Message m:list){
            String k= m.getMessageUserId()+"_"+m.getMessageEssayId();
            map.put(k,m);
        }
        return  map;
    }
    public static HashMap<String,Message> getNoticeMapByMsgList(List<Message> list)throws Exception {
        HashMap<String,Message> map=new HashMap<>();
        for(Message m:list){
            String k= m.getMessageUserId()+"_"+m.getMessageEssayId();
            map.put(k,m);
        }
        return  map;
    }

    public static void cacheMsgByUserAndType(User user, List<Message> list, CacheClient cacheClient)throws Exception {
        String newsKey=Constants.MC_NEWS_MSG_KEY_PREFIX+user.getUserId();
        String essayKey=Constants.MC_ESSAY_MSG_KEY_PREFIX+user.getUserId();
        String remindKey=Constants.MC_REMIND_MSG_KEY_PREFIX+user.getUserId();

        List<Message> newsList=new ArrayList<>();
        List<Message> essayList=new ArrayList<>();
        List<Message> remindList=new ArrayList<>();
        for(Message m:list){
            Integer i=m.getMessageType();
            if(i==0||i==5){
                remindList.add(m);
            }else if(i==1||i==2||i==3){
                essayList.add(m);
            }else if(i==4){
                newsList.add(m);
            }
        }
        HashMap<String,Message> newsMap=getMapByMsgList(newsList);

        HashMap<String,Message> essayMap=getMapByMsgList(essayList);

        HashMap<String,Message> remindMap=getMapByMsgList(remindList);

        if(newsList.size()>0){
            cacheClient.set(newsKey,Constants.REMIND_MSG_EXPIRE_SECOND,newsMap);
        }
        if(essayList.size()>0){
            cacheClient.set(essayKey,Constants.REMIND_MSG_EXPIRE_SECOND,essayMap);
        }
        if(remindMap.size()>0){
            cacheClient.set(remindKey,Constants.REMIND_MSG_EXPIRE_SECOND,remindMap);
        }
    }
    public static void updateMsgInCache(User user, Integer type,List<Message> messageList, CacheClient cacheClient)throws Exception  {

        Integer i = type;
        String remindKey = Constants.MC_MSG_KEY_PREFIX_MAP.get(i) + user.getUserId();
        //msg in cache
        GetsResponse<Object> objectGetsResponse = cacheClient.gets(remindKey);
        if (objectGetsResponse != null) {
            //all the msg of this type under the person
            HashMap<String, Message> remindMap = (HashMap<String, Message>) objectGetsResponse.getValue();
            //msg will be read(reduce from cache)
            for (Message m : messageList) {
                String k = m.getMessageUserId() + "_" + m.getMessageEssayId();
                if (remindMap.get(k) != null) {
                    remindMap.remove(k);
                }
            }
            if (remindMap.size() > 0) {
                cacheClient.set(remindKey, Constants.REMIND_MSG_EXPIRE_SECOND, remindMap);
            } else {
                cacheClient.delete(remindKey);
            }
        }
    }


    public static HashMap<String, Message> getMsgByTypeFromCache (Integer userId,Integer type,CacheClient cacheClient)throws Exception {
        HashMap<String, Message> res=null;
        Integer i = type;
        String remindKey = Constants.MC_MSG_KEY_PREFIX_MAP.get(i) + userId;
        GetsResponse<Object> objectGetsResponse = cacheClient.gets(remindKey);
        if (objectGetsResponse != null) {
            HashMap<String, Message> remindMap = (HashMap<String, Message>) objectGetsResponse.getValue();
            if (remindMap!=null&&remindMap.size() > 0) {
                return remindMap;
            }
        }
        return null;
    }*/

    public Boolean judgeCollected(Integer userId,Integer essayId)throws Exception {
        if(userId==null)
            return null;
        Collection pc=new Collection();
        pc.setUserId(userId);
        pc.setEssayId(essayId);
        Integer r=collectionMapper.countCollectionByCondition(pc);
        if(r!=null&&r>0){
            return true;
        }else{
            return false;
        }
    }

    public Boolean judgeConcerned(Integer userFromId,Integer userToId)throws Exception {
        if(userFromId==null)
            return null;
        Concern pc=new Concern();
        pc.setUserFromId(userFromId);
        pc.setUserToId(userToId);
        Integer r=concernMapper.countConcernByCondition(pc);
        if(r!=null&&r>0){
            return true;
        }else{
            return false;
        }
    }

    public Boolean judgeEssayRecommended(Integer userId, Integer essayId)throws Exception {
        if(userId==null)
            return null;
        Recommend pc=new Recommend();
        pc.setUserId(userId);
        pc.setEssayId(essayId);
        Integer r=recommendMapper.countRecommendByCondition(pc);
        if(r!=null&&r>0){
            return true;
        }else{
            return false;
        }
    }

    public String getAuthByUserId(Integer userId) throws Exception {
        User pe=new User();
        pe.setUserId(userId);
        User user=userMapper.getUserByAccurateCondition(pe).get(0);
        return user.getAuthId();
    }
    public void judgeEssayListConcerned(Integer userFromId,List<Essay> essayList)throws Exception {
        for(Essay e:essayList){
            e.setConcerned(judgeConcerned(userFromId,e.getUserId()));
        }
    }

    public void judgeEssayListRecommended(Integer userId, List<Essay> essayList)throws Exception {
        for(Essay e:essayList){
            e.setRecommended(judgeEssayRecommended(userId,e.getEssayId()));
        }
    }


    public void setEssayAuthId(List<Essay> essayList)throws Exception {
        for(Essay e:essayList){
            e.setAuthId(getAuthByUserId(e.getUserId()));
        }
    }

    /**
     * 设置是否推荐、关注、权限ID
     * @param userId
     * @param essayList
     * @throws Exception
     */
    public void judgeEssayListRecommendedAndConcernedAndAuthId(Integer userId, List<Essay> essayList)throws Exception {
        for(Essay e:essayList){
            e.setConcerned(judgeConcerned(userId,e.getUserId()));
            e.setRecommended(judgeEssayRecommended(userId,e.getEssayId()));
            e.setAuthId(getAuthByUserId(e.getUserId()));
        }
    }


    public Boolean judgeCommentRecommended(Integer userId,Integer commentId)throws Exception {
        if(userId==null)
            return null;
        Recommend pc=new Recommend();
        pc.setUserId(userId);
        pc.setCommentId(commentId);
        Integer r=recommendMapper.countRecommendByCondition(pc);
        if(r!=null&&r>0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 设置是否点赞、权限ID
     * @param userId
     * @param essayList
     * @throws Exception
     */
    public void setCommentListRecommendedAndAuthId(Integer userId, List<Comment> essayList)throws Exception {
        for(Comment c:essayList){
            c.setRecommended(judgeCommentRecommended(userId,c.getCommentId()));
            c.setAuthId(getAuthByUserId(c.getUserId()));
        }
    }


    /**
     * 获取用户的认证信息
     * @param user
     * @return
     * @throws Exception
     */
    public Identification getIdentificationByUser(User user)throws Exception {
        int fans=0;
        int pic=0;
        int nickname=0;
        int signature=0;
        int essays=0;
        int isIdentified=0;


        Integer fansNum=user.getFans();
        String picStr=user.getUserPic();
        Integer nicknameChanged=user.getNicknameChanged();
        String signatureStr =user.getSignature();
        Integer essaysNum;
        String authStr =user.getAuthId();

        String str=StringUtil.concealPhoneNumber(user.getPhoneNumber());

        EssayParam pe=new EssayParam();
        pe.setUserIdList(Arrays.asList(user.getUserId()));
        pe.setRecommendNumFrom(Constants.RECOMMEND_NUM_FROM);
        List list=essayMapper.getEssayByCondition(pe);
        essaysNum=list.size();

        if(fansNum!=null&&fansNum>=Constants.INDENTIFICATION_FANS_NUM){
            fans=1;
        }
        if(!StringUtil.isEmpty(picStr)){
            pic=1;
        }
        if(nicknameChanged==1){
            nickname=1;
        }
        if(!StringUtil.isEmpty(signatureStr)){
            signature=1;
        }
        if(essaysNum>=Constants.INDENTIFICATION_RECOMMEND_ESSAY_NUM){
            essays=1;
        }
        int needIdentify=fans*pic*nickname*signature*essays;
        if(authStr!=null&&authStr.equals("ROLE_IDENTIFICATION")){
            isIdentified=1;
        }
        else if(authStr!=null&&authStr.equals("ROLE_USER")&&needIdentify==1){
//            User newUser=new User();
//            newUser.setUserId(user.getUserId());
//            newUser.setAuthId("ROLE_IDENTIFICATION");
//            newUser.setAuthenticationTime(DateUtil.getDateTime("dateFormat1",new Date()));
//            int updateUser=userMapper.updateUser(newUser);
//            logger.info("updateUser   result:  "+updateUser);
//            if(updateUser!=1){
//                throw new Exception("updateUser更新错误:"+newUser);
//            }
        	AuthAudit authAudit = new AuthAudit();
        	authAudit.setState((byte) 1);
        	authAudit.setUserId(user.getUserId().longValue());
        	List<AuthAudit> authAudits = authAuditMapper.getAuthAudit(authAudit);
        	if(!authAudits.isEmpty() || authAudits.size() > 0){
        		isIdentified = 2;
        	}
        }

        return new Identification(pic,nickname,essays,signature,fans,isIdentified);

    }


}