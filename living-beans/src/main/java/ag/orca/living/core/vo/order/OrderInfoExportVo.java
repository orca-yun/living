package ag.orca.living.core.vo.order;

import ag.orca.living.core.BaseBean;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class OrderInfoExportVo extends BaseBean {

    /**
     * 下单日期
     */
    @ExcelProperty("下单日期")
    @ColumnWidth(20)
    private LocalDate orderDate;

    @ExcelProperty("订单号")
    @ColumnWidth(30)
    private String orderNo;

    @ExcelProperty("渠道名称")
    @ColumnWidth(15)
    private String channelName;

    @ExcelProperty("房间名称")
    @ColumnWidth(20)
    private String roomName;

    /**
     * 下单商品名称
     */
    @ExcelProperty("商品类型")
    @ColumnWidth(15)
    private String goodsTypeName;

    /**
     * 下单商品名称
     */
    @ExcelProperty("商品名称")
    @ColumnWidth(30)
    private String goodsName;

    /**
     * 下单商品名称
     */
    @ExcelProperty("限单")
    @ColumnWidth(10)
    private String goodsBoundsName;


    @ExcelProperty("下单人")
    @ColumnWidth(10)
    private String userName;

    @ExcelProperty("下单时间")
    @ColumnWidth(25)
    private LocalDateTime orderTime;

    @ExcelProperty("成交时间")
    @ColumnWidth(25)
    private LocalDateTime tradeTime;

    /**
     * 商品原价
     */
    @ExcelProperty("商品原价(元)")
    @ColumnWidth(25)
    private Double originalPrice;

    @ExcelProperty("商品现价(元)")
    @ColumnWidth(25)
    private Double price;

    @ExcelProperty("支付金额(元)")
    @ColumnWidth(25)
    private Double realAmt;

    @ExcelProperty("订单状态")
    @ColumnWidth(15)
    private String orderStatus;

    @ExcelProperty("姓名")
    @ColumnWidth(20)
    private String recipientName;

    @ExcelProperty("手机号")
    @ColumnWidth(25)
    private String recipientPhone;


}
