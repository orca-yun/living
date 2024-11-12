package ag.orca.living.core.repo.room.manage;

import ag.orca.living.common.CommonConvert;
import ag.orca.living.common.OrcaAssert;
import ag.orca.living.core.convert.*;
import ag.orca.living.core.entity.room.*;
import ag.orca.living.core.repo.channel.ChannelInfoRepo;
import ag.orca.living.core.repo.room.*;
import ag.orca.living.core.repo.stream.manage.LivingCoursewareManage;
import ag.orca.living.core.vo.room.LivingRoomShareVo;
import ag.orca.living.core.vo.stream.LivingCoursewareVo;
import ag.orca.living.core.vo.stream.LivingKeyDecryptVo;
import ag.orca.living.util.EncryptUtil;
import ag.orca.living.util.I18nUtil;
import com.google.common.collect.Lists;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static ag.orca.living.common.OrcaConst.SPLIT;
import static ag.orca.living.common.OrcaConst.SPLIT_REGEX;

@Component
public class LivingRoomManage {

    private static final int ENCRYPT_LEN = 4;


    @Resource
    LivingRoomRepo roomRepo;

    @Resource
    LivingRoomInteractRepo interactRepo;

    @Resource
    LivingRoomPermissionRepo permissionRepo;

    @Resource
    LivingRoomPageRepo pageRepo;

    @Resource
    LivingRoomMarketGoodsRepo goodsRepo;

    @Resource
    LivingRoomShortUrlRepo roomShortUrlRepo;

    @Resource
    ChannelInfoRepo channelInfoRepo;

    @Resource
    LivingRoomMarketGoodsItemRepo livingRoomMarketGoodsItemRepo;

    @Resource
    LivingRoomMarketGiftItemRepo livingRoomMarketGiftItemRepo;

    @Resource
    LivingCoursewareManage coursewareManage;


    @Transactional(rollbackFor = Exception.class)
    public void saveAndBuildLivingRoom(LivingRoom room) {
        roomRepo.save(room);
        LivingRoomInteract interact = LivingRoomInteractConvert.buildFromLivingRoomInteract(room);
        interactRepo.save(interact);
        LivingRoomPage page = LivingRoomPageConvert.buildFromLivingRoom(room);
        pageRepo.save(page);
        LivingRoomPermission permission = LivingRoomPermissionConvert.buildFromLivingRoom(room);
        permissionRepo.save(permission);
        LivingRoomMarketGoods goods = LivingRoomMarketGoodsConvert.buildFromLivingRoom(room);
        goodsRepo.save(goods);
    }


    public Pair<String, String> encryptToKey(Long roomId, Long channelId) {
        Optional<LivingRoom> optional = roomRepo.findLivingRoomById(roomId);
        OrcaAssert.match(optional.isEmpty(), I18nUtil.getMessage("room.encode.key.error"));
        LivingRoom room = optional.get();
        //查询默认渠道
        if (Objects.isNull(channelId)) {
            channelId = channelInfoRepo.findDefaultChannelId(room.getOrgId());
        }
        //channelId = Objects.isNull(channelId) ? 0L : channelId;
        String plain = room.getOrgId() +
                SPLIT + room.getId() +
                SPLIT + room.getSharePwd() +
                SPLIT + channelId;
        String key = EncryptUtil.desEncrypt(plain);
        OrcaAssert.match(Objects.isNull(key), I18nUtil.getMessage("des.encrypt.err"));
        LivingRoomShortUrl shortUrl = roomShortUrlRepo.findOrSaveByRoomAndChannelId(room, channelId);
        // key 短链接
        return Pair.of(key, shortUrl.getRandomStr());
    }

    public LivingKeyDecryptVo decryptKeyToVo(String key) {
        String m = EncryptUtil.desDecrypt(key);
        OrcaAssert.match(Objects.isNull(m), I18nUtil.getMessage("room.decode.key.error"));
        String[] args = m.split(SPLIT_REGEX);
        OrcaAssert.match(args.length != ENCRYPT_LEN, I18nUtil.getMessage("room.decode.key.error"));
        return LivingKeyDecryptVo.build(args);
    }

    public Pair<LivingKeyDecryptVo, LivingRoomShareVo> decryptToRoomInfo(String key) {
        LivingKeyDecryptVo vo = decryptKeyToVo(key);
        Optional<LivingRoom> optional = roomRepo.findLivingRoomById(vo.getRoomId());
        OrcaAssert.match(optional.isEmpty(), I18nUtil.getMessage("room.not.exists"));
        LivingRoom room = optional.get();
        OrcaAssert.match(!Objects.equals(optional.get().getSharePwd(), vo.getSharePwd()),
                I18nUtil.getMessage("room.share.pwd.error"));
        LivingRoomShareVo shareVo = LivingRoomConvert.entityToShareVo(room);
        assert shareVo != null;
        List<Runnable> runnables = Lists.newArrayList(
                () -> buildRoomExtends(shareVo, room),
                () -> buildCoursewares(shareVo, room),
                () -> buildGiftItems(shareVo, room),
                () -> buildGoodsItems(shareVo, room));
        List<CompletableFuture<Void>> voids = runnables.stream().map(CompletableFuture::runAsync).toList();
        CompletableFuture.allOf(voids.toArray(new CompletableFuture[]{})).join();
        return Pair.of(vo, shareVo);
    }

    private void buildRoomExtends(LivingRoomShareVo shareVo, LivingRoom room) {
        Optional<LivingRoomInteract> interact = interactRepo.findLivingRoomInteractByRoomId(room.getId());
        Optional<LivingRoomPage> page = pageRepo.findLivingRoomPageByRoomId(room.getId());
        Optional<LivingRoomPermission> permission = permissionRepo.findLivingRoomPermissionByRoomId(room.getId());
        Optional<LivingRoomMarketGoods> goods = goodsRepo.findLivingRoomMarketGoodsByRoomId(room.getId());
        shareVo.setPermission(LivingRoomPermissionConvert.entityToShareVo(permission.orElse(null)));
        shareVo.setGoods(LivingRoomMarketGoodsConvert.entityToShareVo(goods.orElse(null)));
        shareVo.setPage(LivingRoomPageConvert.entityToShareVo(page.orElse(null)));
        shareVo.setInteract(LivingRoomInteractConvert.entityToShareVo(interact.orElse(null)));
    }

    private void buildCoursewares(LivingRoomShareVo shareVo, LivingRoom room) {
        List<LivingCoursewareVo> coursewares = coursewareManage.findCoursewareListByRoomId(room.getId());
        shareVo.setCoursewares(coursewares);
    }

    private void buildGiftItems(LivingRoomShareVo shareVo, LivingRoom room) {
        List<LivingRoomMarketGiftItem> gifts = livingRoomMarketGiftItemRepo.findListByRoomId(room.getId());
        shareVo.setGiftItems(CommonConvert.map(gifts, LivingRoomMarketGiftItemConvert::entityToVo));
    }

    private void buildGoodsItems(LivingRoomShareVo shareVo, LivingRoom room) {
        List<LivingRoomMarketGoodsItem> goods = livingRoomMarketGoodsItemRepo.findListByRoomIdForShare(room.getId());
        shareVo.setGoodsItems(CommonConvert.map(goods, LivingRoomMarketGoodsItemConvert::entityToVo));
    }

}
