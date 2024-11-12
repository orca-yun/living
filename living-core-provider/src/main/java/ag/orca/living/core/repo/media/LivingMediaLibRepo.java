package ag.orca.living.core.repo.media;

import ag.orca.living.common.OrcaAssert;
import ag.orca.living.core.convert.VideoInfoConvertConvert;
import ag.orca.living.core.convert.VideoInfoGenConvert;
import ag.orca.living.core.dao.media.LivingMediaLibMapper;
import ag.orca.living.core.dao.room.LivingRoomMapper;
import ag.orca.living.core.entity.media.LivingMediaLib;
import ag.orca.living.core.event.VideoInfoConvertEvent;
import ag.orca.living.core.event.VideoInfoGenEvent;
import ag.orca.living.util.I18nUtil;
import ag.orca.living.util.JsonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Producer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Repository
public class LivingMediaLibRepo {

    @Resource
    LivingMediaLibMapper mediaLibMapper;

    @Resource
    LivingRoomMapper roomMapper;


    @Resource
    Producer<VideoInfoGenEvent> videoInfoGenEventProducer;

    @Resource
    Producer<VideoInfoConvertEvent> videoInfoConvertEventProducer;

    public List<LivingMediaLib> findListByOrgIdAndNameLike(Long orgId, String name) {
        return mediaLibMapper.findListByOrgIdAndNameLike(orgId, name);
    }

    @Transactional(rollbackFor = Exception.class)
    public void remove(Long orgId, List<Long> ids) {
        int cnt = roomMapper.countMediaWithUsed(ids);
        OrcaAssert.match(cnt > 0, I18nUtil.getMessage("media.lib.item.used"));
        mediaLibMapper.logicDel(orgId, ids, LocalDateTime.now());
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(LivingMediaLib lib) {
        Optional<LivingMediaLib> optional = findSameNameMedia(lib.getOrgId(), lib.getName());
        OrcaAssert.match(optional.isPresent(), I18nUtil.getMessage("name.repeat"));
        mediaLibMapper.insert(lib);
        VideoInfoGenEvent event = VideoInfoGenConvert.livingMediaLibToVideoGenEvent(lib, 10);
        videoInfoGenEventProducer.sendAsync(event)
                .thenAccept(messageId ->
                        log.info("[视频信息生成] msg: {}", JsonUtil.beanToJson(event)));
        VideoInfoConvertEvent convertEvent = VideoInfoConvertConvert.livingMediaLibToVideoConvertEvent(lib);
        videoInfoConvertEventProducer.sendAsync(convertEvent)
                .thenAccept(messageId ->
                        log.info("[视频转码生成] msg: {}", JsonUtil.beanToJson(convertEvent)));
    }

    public Optional<LivingMediaLib> findMediaById(Long id) {
        return Optional.ofNullable(mediaLibMapper.findById(id));
    }

    /**
     * 补全 图片信息 与 时长
     *
     * @param id
     * @param accessUrl
     * @param duration
     * @param capacity
     */
    @Transactional(rollbackFor = Exception.class)
    public void renderImageInfo(Long id, String accessUrl, Long duration, Long capacity) {
        LivingMediaLib lib = new LivingMediaLib();
        lib.setImage(accessUrl);
        lib.setDuration(duration);
        lib.setId(id);
        lib.setCapacity(capacity);
        mediaLibMapper.update(lib);
    }

    @Transactional(rollbackFor = Exception.class)
    public void renderConvertPathName(Long id, String key, Integer convertStatus) {
        LivingMediaLib lib = new LivingMediaLib();
        lib.setPathName(key);
        lib.setId(id);
        lib.setConvertStatus(convertStatus);
        mediaLibMapper.update(lib);
    }


    public Optional<Long> totalStorage(Long orgId) {
        return Optional.ofNullable(mediaLibMapper.totalStorage(orgId));
    }


    @Transactional(rollbackFor = Exception.class)
    public void editMediaLibName(Long orgId, Long id, String name) {
        Optional<LivingMediaLib> optional = findSameNameMedia(orgId, name);
        optional.ifPresent(s -> OrcaAssert.match(!Objects.equals(s.getId(), id), I18nUtil.getMessage("name.repeat")));
        LivingMediaLib lib = new LivingMediaLib();
        lib.setId(id);
        lib.setName(name);
        mediaLibMapper.update(lib);
    }

    public Optional<LivingMediaLib> findSameNameMedia(Long orgId, String name) {
        return Optional.ofNullable(mediaLibMapper.findSameNameMedia(orgId, name));
    }


}
