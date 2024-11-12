package ag.orca.living.provider;

import ag.orca.living.api.seq.LivingSeqService;
import ag.orca.living.core.FixedLenGen;
import ag.orca.living.core.IdGen;
import ag.orca.living.core.Md5Util;
import ag.orca.living.core.RandomUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static ag.orca.living.common.OrcaConst.DEF_ORDER_FORMATTER;
import static ag.orca.living.common.OrcaConst.DEF_SEQ_LEN;

@Slf4j
@DubboService
public class LivingSeqProvider implements LivingSeqService {

    private static final int MAX_BATCH = 500;

    @Resource
    FixedLenGen fixedLenGen;


    @Override
    public Long nextId() {
        return IdGen.generateId();
    }

    @Override
    public List<Long> batchNextId(int batch) {
        batch = batch <= 0 ? 1 : batch;
        batch = Math.min(batch, MAX_BATCH);
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < batch; i++) {
            ids.add(nextId());
        }
        return ids;
    }


    @Override
    public String randomStr(int len) {
        return RandomUtil.randomString(len);
    }

    @Override
    public List<String> batchRandomStr(int batch, int len) {
        batch = batch <= 0 ? 1 : batch;
        batch = Math.min(batch, MAX_BATCH);
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < batch; i++) {
            ids.add(RandomUtil.randomString(len));
        }
        return ids;
    }

    @Override
    public String randomStrFromMd5(String input, int len) {
        String md5 = Md5Util.genMd5(input);
        return md5.substring(0, len);
    }

    @Override
    public String uuid() {
        return IdGen.uuid();
    }


    @Override
    public Long fixLenNextId(int len) {
        return fixedLenGen.nextId(len);
    }

    @Override
    public String nextIdContainsTimestamp(int seqLen) {
        return nextIdContainsTimestamp(seqLen, null);
    }

    @Override
    public String nextIdContainsTimestamp(int seqLen, String formatter) {
        seqLen = (seqLen <= 0 ? DEF_SEQ_LEN : seqLen);
        Long s = fixLenNextId(seqLen);
        String x = formatLen(seqLen, s);
        DateTimeFormatter f = StringUtils.isEmpty(formatter) ? DEF_ORDER_FORMATTER : DateTimeFormatter.ofPattern(formatter);
        String prev = LocalDateTime.now().format(f);
        return prev + x;
    }

    private static String formatLen(int len, long number) {
        return String.format("%0" + len + "d", number);
    }


}