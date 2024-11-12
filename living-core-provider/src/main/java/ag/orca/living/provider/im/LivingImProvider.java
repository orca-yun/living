package ag.orca.living.provider.im;

import ag.orca.living.api.im.LivingImService;
import ag.orca.living.core.auth.AuthInfo;
import ag.orca.living.core.repo.im.LivingImRepo;
import ag.orca.living.core.ro.im.RobotSendRo;
import ag.orca.living.core.ro.im.RollbackMsgRo;
import ag.orca.living.core.ro.im.SendReplyMsgRo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

@Slf4j
@DubboService
public class LivingImProvider implements LivingImService {

    @Resource
    LivingImRepo livingImRepo;


    @Override
    public void rollbackMsg(Long roomId, RollbackMsgRo ro, AuthInfo authInfo) {
        livingImRepo.rollbackMsg(roomId, ro.getMsgUid(), authInfo);
    }

    @Override
    public void sendMsg(Long roomId, SendReplyMsgRo ro, AuthInfo authInfo) {
        livingImRepo.sendMsg(roomId, ro.getData(), ro.getReplyData(), ro.getMsgType(), authInfo);
    }

    @Override
    public void robotSendMsg(Long roomId, RobotSendRo ro) {
        livingImRepo.robotSendMsg(roomId, ro.getRobotId(), ro.getData(), ro.getReplyData());
    }
}
