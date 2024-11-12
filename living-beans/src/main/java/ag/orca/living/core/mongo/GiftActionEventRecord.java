package ag.orca.living.core.mongo;

import ag.orca.living.core.BaseBean;
import ag.orca.living.core.vo.room.LivingRoomMarketGiftItemVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
//@Document
public class GiftActionEventRecord extends BaseBean {

    @Id
    private String id;


    private Long roomId;

    private List<LivingRoomMarketGiftItemVo> items;


    @Indexed(background = true)
    private Long cts;

    @Indexed(background = true)
    private LocalDateTime createTime;


    private LocalDateTime updateTime;
}
