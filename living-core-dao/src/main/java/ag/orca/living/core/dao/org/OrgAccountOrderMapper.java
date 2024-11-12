package ag.orca.living.core.dao.org;

import ag.orca.living.core.entity.org.OrgAccountOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

@Mapper
public interface OrgAccountOrderMapper {

    int insert(OrgAccountOrder order);


    int markStatus(@Param("id") Long id,
                   @Param("status") Integer status,
                   @Param("dateTime") LocalDateTime dateTime);
}
