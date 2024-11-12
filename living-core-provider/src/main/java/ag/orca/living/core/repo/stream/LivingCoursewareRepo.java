package ag.orca.living.core.repo.stream;

import ag.orca.living.api.seq.LivingSeqService;
import ag.orca.living.common.CommonConvert;
import ag.orca.living.common.FileMimeEnum;
import ag.orca.living.common.OrcaAssert;
import ag.orca.living.core.convert.LivingCoursewareConvert;
import ag.orca.living.core.dao.stream.LivingCoursewareItemMapper;
import ag.orca.living.core.dao.stream.LivingCoursewareMapper;
import ag.orca.living.core.entity.room.LivingRoom;
import ag.orca.living.core.entity.stream.LivingCourseware;
import ag.orca.living.core.entity.stream.LivingCoursewareItem;
import ag.orca.living.core.enums.CoursewareConvertStatusEnum;
import ag.orca.living.core.event.CoursewareConvertEvent;
import ag.orca.living.core.repo.room.LivingRoomRepo;
import ag.orca.living.core.repo.storage.ObjectStorageRepo;
import ag.orca.living.errors.OrcaException;
import ag.orca.living.util.I18nUtil;
import ag.orca.living.util.JsonUtil;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pulsar.client.api.Producer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static ag.orca.living.common.OrcaConst.MONTH_FORMAT;
import static ag.orca.living.common.OrcaConst.PATH_SPLIT;

@Slf4j
@Repository
public class LivingCoursewareRepo {

    @Resource
    LivingRoomRepo livingRoomRepo;

    @Resource
    LivingCoursewareMapper coursewareMapper;

    @Resource
    LivingCoursewareItemMapper coursewareItemMapper;

    @Resource
    AmazonS3 amazonS3;

    @Resource
    ObjectStorageRepo objectStorageRepo;

    @Resource
    Producer<CoursewareConvertEvent> coursewareConvertEventProducer;

    @DubboReference
    LivingSeqService seqService;

    public List<LivingCourseware> findCoursewareListByRoomId(Long roomId) {
        return coursewareMapper.findListByRoomId(roomId);
    }

    public List<LivingCoursewareItem> findCoursewareItemListByRoomId(Long roomId) {
        return coursewareItemMapper.findListByRoomId(roomId);
    }

    @Transactional(rollbackFor = Exception.class)
    public LivingCourseware saveConvertPdfToImageRecord(Long roomId, String fileName, String bucket, String key) {
        Optional<LivingRoom> room = livingRoomRepo.findLivingRoomById(roomId);
        OrcaAssert.match(room.isEmpty(), I18nUtil.getMessage("room.not.exists"));
        LivingCourseware courseware = LivingCoursewareConvert.buildEntityFromRoom(room.get(), fileName);
        coursewareMapper.insert(courseware);
        CoursewareConvertEvent event = CoursewareConvertEvent.builder().recordId(courseware.getId())
                .roomId(roomId)
                .fileName(fileName)
                .bucket(bucket)
                .key(key).build();
        coursewareConvertEventProducer.sendAsync(event)
                .thenAccept(messageId ->
                        log.info("[转换课件] roomId: {}, msg: {}", roomId, JsonUtil.beanToJson(event)));

        return courseware;


    }


    public void startConvertPdfToImageRecord(CoursewareConvertEvent event) {
        LivingCourseware courseware = coursewareMapper.findById(event.getRecordId());
        if (Objects.isNull(courseware)) {
            return;
        }

        String bucket = event.getBucket();
        String key = event.getKey();
        // 下载临时文件
        GetObjectRequest request = new GetObjectRequest(bucket, key);
        String uid = seqService.uuid();
        String df = DateFormatUtils.format(new Date(), MONTH_FORMAT);
        List<File> images = new ArrayList<>();
        List<String> imagePaths = new ArrayList<>();
        File f = null;
        CoursewareConvertStatusEnum status = CoursewareConvertStatusEnum.ING;
        try {
            f = File.createTempFile(uid, FileMimeEnum.PDF.getSuffix());
            ObjectMetadata object = amazonS3.getObject(request, f);
            PDDocument doc = Loader.loadPDF(f);
            PDFRenderer renderer = new PDFRenderer(doc);
            String imageBucket = objectStorageRepo.imageBucket();
            for (int i = 0; i < doc.getNumberOfPages(); i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, 200);

                String fileKey = df + PATH_SPLIT + uid + "_" + i + FileMimeEnum.PNG.getSuffix();
                File t = File.createTempFile(uid + "_" + i, FileMimeEnum.PNG.getSuffix());
                ImageIO.write(image, FileMimeEnum.PNG.getType(), t);
                String p = putImageObject(imageBucket, fileKey, t);
                imagePaths.add(p);
                images.add(t);
            }
            batchSaveCoursewareItems(courseware, imagePaths);
            status = CoursewareConvertStatusEnum.SUCCESS;
        } catch (IOException e) {
            status = CoursewareConvertStatusEnum.FAILED;
            throw OrcaException.error("转码文件错误");
        } finally {
            if (!CollectionUtils.isEmpty(images)) {
                images.forEach(File::delete);
            }
            if (Objects.nonNull(f)) {
                f.delete();
            }
            coursewareMapper.updateStatusById(status.getCode(), event.getRecordId());
        }
    }


    private void batchSaveCoursewareItems(LivingCourseware courseware, List<String> imagePaths) {
        List<LivingCoursewareItem> items = CommonConvert.map(imagePaths, k -> LivingCoursewareConvert.buildItemEntity(courseware, k));
        items.forEach(coursewareItemMapper::insert);
    }

    private String putImageObject(String bucket,
                                  String key,
                                  File file) {
        PutObjectRequest objectRequest = new PutObjectRequest(bucket, key, file);
        objectRequest
                .withStorageClass(StorageClass.Standard)
                .withCannedAcl(CannedAccessControlList.PublicRead);
        PutObjectResult result = amazonS3.putObject(objectRequest);
        return objectStorageRepo.imgPath(bucket, key);
    }


    public void logicRemoveCoursewareById(Long id) {
        LocalDateTime n = LocalDateTime.now();
        coursewareMapper.logicRemoveCoursewareById(id, n);
        coursewareItemMapper.logicRemoveCoursewareItemByCoursewareId(id, n);
    }
}
