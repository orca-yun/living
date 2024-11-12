package ag.orca.living.api.seq;

import ag.orca.living.dubbo.annotation.ExcludeDubboLog;

import java.util.List;

@ExcludeDubboLog
public interface LivingSeqService {

    /**
     * 只取一个
     *
     * @return ID
     */
    Long nextId();


    /**
     * 最多获取 500 个
     *
     * @param batch 最小值 1 最大值 500
     * @return ID列表
     */
    List<Long> batchNextId(int batch);


    /**
     * 生成单个 固定长度ID
     *
     * @param len 长度
     * @return ID
     */
    Long fixLenNextId(int len);


    /**
     * 生成一个
     *
     * @param seqLen
     * @return
     */
    String nextIdContainsTimestamp(int seqLen);

    String nextIdContainsTimestamp(int seqLen, String formatter);


    /**
     * 随机字符串 abcdefghijklmnopqrstuvwxyz 1234567890
     *
     * @param len
     * @return
     */
    String randomStr(int len);

    /**
     * 批量的随机字符串 abcdefghijklmnopqrstuvwxyz 1234567890
     *
     * @param batch 批量大小
     * @param len   长度
     * @return 随机串
     */
    List<String> batchRandomStr(int batch, int len);


    /**
     * 随机字符串通过MD5
     *
     * @param input 文本
     * @param len   截取长度
     * @return md5串
     */
    String randomStrFromMd5(String input, int len);


    String uuid();


}
