package ag.orca.living.api.share;


import ag.orca.living.core.auth.AuthInfo;

public interface ShareUserStatService {

    void reportStat(AuthInfo info, String ua);
}
