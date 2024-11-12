package ag.orca.living.api.stream;


import ag.orca.living.core.vo.stream.LivingCoursewareVo;

import java.util.List;

public interface LivingCoursewareService {
    /***
     * 下载PDF转换成图片
     * @param roomId
     * @param fileName
     * @param bucket
     * @param key
     * @return
     */
    LivingCoursewareVo triggerConvertPdfToImage(Long roomId, String fileName, String bucket, String key);

    /**
     * 获取文件地址
     *
     * @param roomId
     * @return
     */
    List<LivingCoursewareVo> findListByRoomId(Long roomId);

    /**
     * 根据ID 删除课件
     *
     * @param id
     */
    void removeCourseware(Long id);
}
