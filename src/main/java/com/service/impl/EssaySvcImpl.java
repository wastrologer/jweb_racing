package com.service.impl;

import com.common.cache.CacheClient;
import com.common.entity.ReturnMessage;
import com.common.utils.SvcUtils;
import com.common.utils.UserInfoUtil;
import com.dao.mapper.EssayMapper;
import com.dao.mapper.TopicMapper;
import com.dao.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pojo.*;
import com.service.IEssaySvc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@Service
public class EssaySvcImpl implements IEssaySvc {
    private Integer defaultPageSize=100;
    protected static final Logger logger = LoggerFactory.getLogger(EssaySvcImpl.class);

    @Resource
    private CacheClient cacheClient;
    @Resource
    EssayMapper essayMapper;
    @Resource
    TopicMapper topicMapper;
    @Resource
    private SvcUtils svcUtils;
    @Resource
    private UserMapper userMapper;

    public User getUserById(Integer id)throws Exception {
        User user=new User();
        user.setUserId(id);
        return getUserByAccurateCondition(1,user);
    }

    public User getUserByAccurateCondition(Integer mine,User user)throws Exception {
        List<User> result=userMapper.getUserByAccurateCondition(user);
        if(result.size()!=1){
            logger.info(user.toString());
        }
        if(mine!=1){
            UserInfoUtil.concealUserInfo(result);
        }
        return svcUtils.judgeResultList(result);
    }

    @Override
    public PageInfo getEssayByCollectionAndPage(Collection collection, Integer pageNum, Integer pageSize)throws Exception {
        if(pageNum==null)
            pageNum=1;
        if(pageSize==null||pageSize<=0)
            pageSize=defaultPageSize;
        PageHelper.startPage(pageNum,pageSize);
        List<Essay> essayList=essayMapper.getEssayByCollectionCondition(collection);
        for(Essay e:essayList){
            User u=getUserById(e.getUserId());
            e.setUserPic(u.getUserPic());
        }
        PageInfo pageInfo=new PageInfo(essayList);
        return pageInfo;
    }

    @Override
    public PageInfo getEssayByConditionAndPage(EssayParam essayParam, Integer pageNum, Integer pageSize)throws Exception {
        if(pageNum==null)
            pageNum=1;
        if(pageSize==null||pageSize<=0)
            pageSize=defaultPageSize;
        PageHelper.startPage(pageNum,pageSize);
        List<Essay> essayList=essayMapper.getEssayByCondition(essayParam);
        for(Essay e:essayList){
            User u=getUserById(e.getUserId());
            e.setUserPic(u.getUserPic());
        }
        PageInfo pageInfo=new PageInfo(essayList);
        if(essayParam.getHot()){
            List<Essay> es=(List<Essay>)pageInfo.getList();
            for(Essay e:es){
                e.setHot(true);
            }
        }
        return pageInfo;
    }

    @Override
    public PageInfo getSimpleEssayByConditionAndPage(EssayParam essayParam, Integer pageNum, Integer pageSize)throws Exception {
        if(pageNum==null)
            pageNum=1;
        if(pageSize==null||pageSize<=0)
            pageSize=defaultPageSize;
        PageHelper.startPage(pageNum,pageSize);
        List<Essay> essayList=essayMapper.getEssayByCondition(essayParam);
        for(Essay e:essayList){
            User u=getUserById(e.getUserId());
            e.setUserPic(u.getUserPic());
            e.setEssayContent(null);
        }
        PageInfo pageInfo=new PageInfo(essayList);
        if(essayParam.getHot()){
            List<Essay> es=(List<Essay>)pageInfo.getList();
            for(Essay e:es){
                e.setHot(true);
            }
        }
        return pageInfo;
    }

    @Override
    public PageInfo getEssayByConcernAndPage(Integer userId, Integer pageNum)throws Exception {
        return getEssayByUserListAndPage(svcUtils.getUserIdByConcern(userId),1,pageNum);
    }

    @Override
    public PageInfo getEssayByConcernAndPage(Integer userId, Integer pageNum, Integer pageSize)throws Exception {
        List <Integer> list=svcUtils.getUserIdByConcern(userId);
        return getEssayByUserListAndPage(list,1,pageNum,pageSize);
    }

    @Override
    public PageInfo getEssayByUserListAndPage(List<Integer> list,Integer isPublished, Integer pageNum)throws Exception {
        return getEssayByUserListAndPage(list,isPublished,pageNum,defaultPageSize);
    }


    @Override
    public PageInfo getEssayByUserListAndPage(List<Integer> list, Integer isPublished, Integer pageNum, Integer pageSize)throws Exception {
        if(list==null){
            return null;
        }else if(list.size()==0){
            return new PageInfo(new ArrayList<Essay>());
        }
        EssayParam essayParam=new EssayParam();
        essayParam.setIsPublished(isPublished);
        essayParam.setUserIdList(list);
        return getEssayByConditionAndPage(essayParam,pageNum,pageSize);
    }

    @Override
    public Integer countEssayByCondition(Essay essay)throws Exception {
        return essayMapper.countEssayByCondition(essay);
    }

    @Override
    public PageInfo getEssayByFuzzyConditionAndPage(Essay essay, Integer pageNum, Integer pageSize)throws Exception {
        if(pageNum==null)
            pageNum=1;
        if(pageSize==null||pageSize<=0)
            pageSize=defaultPageSize;
        PageHelper.startPage(pageNum,pageSize);
        List<Essay> essayList=essayMapper.getEssayByFuzzyCondition(essay);
        for(Essay e:essayList){
            User u=getUserById(e.getUserId());
            e.setUserPic(u.getUserPic());
        }
        PageInfo pageInfo=new PageInfo(essayList);
        return pageInfo;
    }

    @Override
    public PageInfo getEssayByFuzzyConditionAndPage(Essay essay, Integer pageNum)throws Exception {
        return getEssayByFuzzyConditionAndPage(essay,pageNum,defaultPageSize);
    }

    @Override
    public Essay getEssayByAccurateCondition(Essay essay)throws Exception {
        List<Essay> result=essayMapper.getEssayByAccurateCondition(essay);
        if(result.size()!=1){
            logger.info(essay.toString());
        }
        for(Essay e:result){
            User u=getUserById(e.getUserId());
            e.setUserPic(u.getUserPic());
        }
        return svcUtils.judgeResultList(result);
    }

    @Override
    public Essay getEssayByEssayId(Integer id)throws Exception {
        Essay p=new Essay();
        p.setEssayId(id);
        return getEssayByAccurateCondition(p);
    }

    @Override
    public Integer addEssay(User user, Essay essay)throws Exception {
        if(user==null)
            return null;
        essay.setUserId(user.getUserId());
        essay.setUserNickname(user.getNickname());
        essay.setCreateTime(new Timestamp(System.currentTimeMillis()));
        if(essay.getRegionId()==null){
            essay.setRegionId(user.getRegionId());
        }
        essay.setRecommendNum(0);
        essay.setClickNum(0);
        essay.setCommentNum(0);
        Integer isPublished=essay.getIsPublished();
        if(isPublished!=null&&isPublished==1){
            essay.setPublishTime(new Timestamp(System.currentTimeMillis()));
        }else{
            essay.setIsPublished(0);
            essay.setPublishTime(null);
        }
        if(essay.getRegionId()>6&&essay.getRegionId()<19){
            essay.setCityId(2);
        }
        int i= essayMapper.addEssay(essay);
        if(i!=1){
            throw new Exception(essay.toString());
        }
        return  essay.getEssayId();
    }

    @Override
    public Integer updateEssayClickNum(ReturnMessage msg)throws Exception {
        Essay pe=new Essay();
        pe.setEssayId(msg.getEssayId());
        pe.setClickNum(1);
        return updateEssay(pe);
    }

    @Override
    public Integer updateEssayCommentNum(ReturnMessage msg)throws Exception {
        Essay pe=new Essay();
        pe.setEssayId(msg.getEssayId());
        pe.setCommentNum(1);
        return updateEssay(pe);
    }

    @Override
    public Integer updateEssayRecommendNum(ReturnMessage msg)throws Exception {
        Essay pe=new Essay();
        pe.setEssayId(msg.getEssayId());
        pe.setRecommendNum(msg.getAddRecommendNum());
        return updateEssay(pe);
    }

    @Override
    public Integer updateEssaySelf(User user, Essay essay)  throws Exception{
        if(essay.getUserId()==null){
            Essay e=getEssayByEssayId(essay.getEssayId());
            essay.setUserId(e.getUserId());
        }
        obstructUpdationOfEssaySelf(essay);
        if(user.getUserId()==(int)essay.getUserId()){
            return updateEssay(essay);
        }
        return null;
    }

    @Override
    public Integer updateEssayByOthers(Essay essay)throws Exception {
        Essay e=new Essay();
        e.setEssayId(essay.getEssayId());
        if(essay.getRecommendNum()!=null&&essay.getRecommendNum()==1){
            e.setRecommendNum(essay.getRecommendNum());
        }
        if(essay.getCommentNum()!=null&&essay.getCommentNum()==1){
            e.setCommentNum(essay.getCommentNum());
        }
        if(essay.getClickNum()!=null&&essay.getClickNum()==1){
            e.setClickNum(essay.getClickNum());
        }
        return updateEssay(e);
    }

    public static void obstructUpdationOfEssaySelf(Essay essay)throws Exception {
        essay.setRecommendNum(null);
        essay.setCommentNum(null);
        essay.setClickNum(null);
        essay.setCreateTime(null);
    }
    public static void obstructUpdationOfEssayByOthers(Essay essay)throws Exception {
        essay.setEssayPic(null);
        essay.setIsPublished(null);
        essay.setEssayTitle(null);
        essay.setEssayContent(null);
        essay.setEssayType(null);
        essay.setCreateTime(null);
        essay.setUserNickname(null);
        essay.setTopicId(null);
    }
//num数量为变化量
    @Override
    public Integer updateEssay(Essay essay)throws Exception {
        Essay e=getEssayByEssayId(essay.getEssayId());
        Integer isPublished=essay.getIsPublished();

        if(isPublished!=null&&isPublished==1&&(e.getIsPublished()==0)){
            essay.setPublishTime(new Timestamp(System.currentTimeMillis()));
        }
        //

        //

        //

        int i= essayMapper.updateEssay(essay);
        if(i!=1){
            throw new Exception(essay.toString());
        }
        return  i;
    }

    //update essay nickname batch
    @Override
    public Integer updateEssayBatch(User user)throws Exception {
        return essayMapper.updateEssayBatch(user);
    }

    @Override
    public Integer deleteEssayById(Integer id)throws Exception {
        int i= essayMapper.deleteEssayById(id);
        if(i!=1){
            throw new Exception("id="+id.toString());
        }
        return  i;
    }

    @Override
    public PageInfo getTopicByConditionAndPage(Topic topic, Integer pageNum, Integer pageSize)throws Exception {
        if(pageNum==null)
            pageNum=1;
        if(pageSize==null||pageSize<=0)
            pageSize=defaultPageSize;
        PageHelper.startPage(pageNum,pageSize);
        List<Topic> list=topicMapper.getTopicByCondition(topic);
        PageInfo pageInfo=new PageInfo(list);
        return pageInfo;
    }

    public Integer getDefaultPageSize() {
        return defaultPageSize;
    }

    public void setDefaultPageSize(Integer defaultPageSize) {
        this.defaultPageSize = defaultPageSize;
    }

    public CacheClient getCacheClient() {
        return cacheClient;
    }

    public void setCacheClient(CacheClient cacheClient) {
        this.cacheClient = cacheClient;
    }

    public EssayMapper getEssayMapper() {
        return essayMapper;
    }

    public void setEssayMapper(EssayMapper essayMapper) {
        this.essayMapper = essayMapper;
    }

    public TopicMapper getTopicMapper() {
        return topicMapper;
    }

    public void setTopicMapper(TopicMapper topicMapper) {
        this.topicMapper = topicMapper;
    }

    public SvcUtils getSvcUtils() {
        return svcUtils;
    }

    public void setSvcUtils(SvcUtils svcUtils) {
        this.svcUtils = svcUtils;
    }

    public UserMapper getUserMapper() {
        return userMapper;
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
