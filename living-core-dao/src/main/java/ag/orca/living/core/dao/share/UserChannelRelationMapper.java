package ag.orca.living.core.dao.share;

import ag.orca.living.core.entity.share.UserChannelRelation;
import ag.orca.living.core.vo.stats.ChannelUserStaticsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface UserChannelRelationMapper {

    int insert(UserChannelRelation relation);

    List<ChannelUserStaticsVo> findChannelStaticsByOrgIdAndBindDate(@Param("orgId") Long orgId,
                                                                    @Param("bindDate") LocalDate bindDate);


    UserChannelRelation findFirstByOpenId(@Param("openId") String openId);
}
