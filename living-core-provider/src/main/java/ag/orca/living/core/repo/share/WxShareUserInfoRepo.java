package ag.orca.living.core.repo.share;

import ag.orca.living.core.convert.UserChannelRelationConvert;
import ag.orca.living.core.dao.share.UserChannelRelationMapper;
import ag.orca.living.core.dao.share.UserInfoMapper;
import ag.orca.living.core.entity.share.UserChannelRelation;
import ag.orca.living.core.entity.share.UserInfo;
import ag.orca.living.core.repo.live.LivingLiveRepo;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static ag.orca.living.common.CacheConst.KEY_USER_MAPPING;

@Repository
public class WxShareUserInfoRepo {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    UserInfoMapper userInfoMapper;

    @Resource
    UserChannelRelationMapper userChannelRelationMapper;

    @Resource
    LivingLiveRepo livingLiveRepo;

    private boolean isSameUserInfo(UserInfo newUserInfo, UserInfo dbUserInfo) {
        return StringUtils.equalsIgnoreCase(newUserInfo.getNickName(), dbUserInfo.getNickName()) &&
                StringUtils.equalsIgnoreCase(newUserInfo.getHeadImageUrl(), dbUserInfo.getHeadImageUrl());
    }

    public UserInfo findUserInfoByUserId(String id) {
        return userInfoMapper.getUserInfoById(id);
    }

    public UserInfo findUserInfoByUserId(Long userId) {
        return findUserInfoByUserId(userId.toString());
    }


    public Optional<String> findUserIdByRandom(Long random) {
        return Optional.ofNullable(stringRedisTemplate.opsForValue().get(KEY_USER_MAPPING + random));
    }

    @Transactional(rollbackFor = Exception.class)
    public UserInfo saveUserInfo(UserInfo userInfo, Long channelId, Long orgId, Long roomId, Long random) {
        UserInfo info = userInfoMapper.findByOpenId(userInfo.getOpenId());
        if (Objects.nonNull(info)) {
            userInfo.setId(info.getId());
            userInfo.setCreateTime(info.getCreateTime());
            if (!isSameUserInfo(userInfo, info)) {
                userInfoMapper.updateByOpenId(userInfo);
            }

            UserChannelRelation db = userChannelRelationMapper.findFirstByOpenId(userInfo.getOpenId());
            if (Objects.isNull(db)) {
                Optional<Long> recordId = livingLiveRepo.getLivingRecordIdByRoomId(roomId);
                UserChannelRelation relation = UserChannelRelationConvert.buildEntity(channelId, orgId, roomId, userInfo, recordId);
                userChannelRelationMapper.insert(relation);
            }
        } else {
            // 数据库内没有
            userInfoMapper.insert(userInfo);
            Optional<Long> recordId = livingLiveRepo.getLivingRecordIdByRoomId(roomId);
            // 保存用户和渠道的关系
            UserChannelRelation relation = UserChannelRelationConvert.buildEntity(channelId, orgId, roomId, userInfo, recordId);
            userChannelRelationMapper.insert(relation);
        }
        // 缓存 随机数 与 用户 ID 映射关系
        stringRedisTemplate.opsForValue().set(KEY_USER_MAPPING + random, userInfo.getId(), 5, TimeUnit.MINUTES);
        return userInfo;

    }

//    public void channelLivingRoomNum(String openId, Long roomId, Long channelId) {
//        // 判断用户是否已经在直播间的Set中  所有的key定时任务释放
//        if (!stringRedisTemplate.opsForSet().isMember(WX_ROOM_USER_PREFIX + roomId, openId)) {
//            // 如果用户不在直播间的Set中，则将用户加入Set
//            stringRedisTemplate.opsForSet().add(WX_ROOM_USER_PREFIX + roomId, openId);
//            // 增加roomId: channelId: num  该直播间 各渠道 分别引流的人数
//            stringRedisTemplate.opsForZSet().incrementScore(ROOM_CHANNEL_USER_PREFIX + roomId, channelId.toString(), 1);
//            // 增加channelId: roomId: num  该渠道 为各直播间 引流人数
//            stringRedisTemplate.opsForZSet().incrementScore(CHANNEL_ROOM_USER_PREFIX + channelId, roomId.toString(), 1);
//        } else {
//            // 如果用户在直播间的Set中，判断用户该次进入渠道和上次是否一致
//            if (stringRedisTemplate.opsForZSet().rank(ROOM_CHANNEL_USER_PREFIX + roomId, channelId.toString()) == null) {
//                stringRedisTemplate.opsForZSet().incrementScore(ROOM_CHANNEL_USER_PREFIX + roomId, channelId.toString(), 1);
//            }
//            if (stringRedisTemplate.opsForZSet().rank(CHANNEL_ROOM_USER_PREFIX + channelId, roomId.toString()) == null) {
//                stringRedisTemplate.opsForZSet().incrementScore(CHANNEL_ROOM_USER_PREFIX + channelId, roomId.toString(), 1);
//            }
//        }
//    }


//    public void cacheLoginNum(UserInfo userInfo, Long channelId, Long orgId) {
//        String redisKey = WX_LOGIN_INFO_PREFIX + userInfo.getOpenId() + "%" + channelId;
//        String userStr = stringRedisTemplate.opsForValue().get(redisKey);
//        if (StringUtils.isBlank(userStr)) {
//            //让登陆人当前23点55分00秒redis缓存失效，重置该登录计算人次
//            LocalDateTime expireTime = LocalDateTime.of(LocalDate.now(), EXPIRE_END_TIME);
//            long seconds = Duration.between(LocalTime.now(), expireTime).getSeconds(); // 获取时间间隔 秒
//            seconds = seconds < minExpireTimeSeconds ? MaxExpireTimeSeconds : seconds;
//            stringRedisTemplate.opsForValue().set(redisKey, JsonUtil.beanToJson(userInfo), seconds, TimeUnit.SECONDS);
//            stringRedisTemplate.opsForZSet().incrementScore(CHANNEL_INCREMENT_PREFIX + orgId, channelId.toString(), 1);
//        }
//    }


}
