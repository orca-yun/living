package ag.orca.living.api.im;

import ag.orca.living.core.auth.AuthInfo;
import ag.orca.living.core.ro.im.RobotSendRo;
import ag.orca.living.core.ro.im.RollbackMsgRo;
import ag.orca.living.core.ro.im.SendReplyMsgRo;

public interface LivingImService {

    void rollbackMsg(Long roomId, RollbackMsgRo ro, AuthInfo authInfo);

    void sendMsg(Long roomId, SendReplyMsgRo ro, AuthInfo authInfo);

    void robotSendMsg(Long roomId, RobotSendRo ro);
}
