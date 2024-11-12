package ag.orca.living.common;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.UUID;

import static ag.orca.living.common.OrcaConst.MONTH_FORMAT;
import static ag.orca.living.common.OrcaConst.PATH_SPLIT;

public class ObjectStorageWrapper {

    public static String buildPathName(String realFileName) {
        return DateFormatUtils.format(new Date(), MONTH_FORMAT) + PATH_SPLIT + realFileName;
    }

    public static String buildRealFileName(FileMimeEnum fileMime) {
        return UUID.randomUUID().toString().replaceAll("-", "") + fileMime.getSuffix();
    }
}
