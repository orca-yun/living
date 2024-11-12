package ag.orca.living.core.dao.share;

import ag.orca.living.core.entity.share.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserInfoMapper {

    UserInfo findByOpenId(@Param("openId") String openId);

    int updateByOpenId(UserInfo info);

    int insert(UserInfo info);

    UserInfo getUserInfoById(@Param("id") String id);

}
