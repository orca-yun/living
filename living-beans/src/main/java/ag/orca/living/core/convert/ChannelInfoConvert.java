package ag.orca.living.core.convert;

import ag.orca.living.core.entity.channel.ChannelInfo;
import ag.orca.living.core.enums.BoolEnum;
import ag.orca.living.core.enums.CommissionMethodEnum;
import ag.orca.living.core.ro.channel.ChannelInfoRo;
import ag.orca.living.core.vo.channel.ChannelInfoVo;

import java.time.LocalDateTime;
import java.util.Objects;

public class ChannelInfoConvert {

    public static ChannelInfo roToCreateEntity(Long orgId, ChannelInfoRo ro) {
        return Objects.isNull(ro) ? null : ChannelInfo.builder()
                .orgId(orgId)
                .channelName(ro.getChannelName())
                .channelId(ro.getChannelId())
                .channelOwner(ro.getChannelOwner())
                .contactPhone(ro.getContactPhone())
                .channelDescription(ro.getChannelDescription())
                .commissionRatio(ro.getCommissionRatio())
                .commissionMethod(ro.getCommissionMethod())
                .commissionPeriod(ro.getCommissionPeriod())
                .bankAccountType(ro.getBankAccountType())
                .receivingUnit(ro.getReceivingUnit())
                .bankAccountNumber(ro.getBankAccountNumber())
                .bankBranch(ro.getBankBranch())
                .settlementCurrency(ro.getSettlementCurrency())
                .createUser(ro.getAccount())
                .updateUser(null)
                .sysInner(BoolEnum.FALSE.getCode())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
    }

    public static ChannelInfo roToUpdateEntity(ChannelInfoRo ro) {
        if (Objects.isNull(ro)) {
            return null;
        }
        CommissionMethodEnum method = CommissionMethodEnum.ofCode(ro.getCommissionMethod());
        return ChannelInfo.builder()
                .id(ro.getId())
                .channelName(ro.getChannelName())
                .channelId(ro.getChannelId())
                .channelOwner(ro.getChannelOwner())
                .contactPhone(ro.getContactPhone())
                .channelDescription(ro.getChannelDescription())
                .commissionRatio(ro.getCommissionRatio())
                .commissionMethod(ro.getCommissionMethod())
                .commissionPeriod(method == CommissionMethodEnum.HANDLER ? null : ro.getCommissionPeriod())
                .bankAccountType(method == CommissionMethodEnum.HANDLER ? null : ro.getBankAccountType())
                .receivingUnit(method == CommissionMethodEnum.HANDLER ? null : ro.getReceivingUnit())
                .bankAccountNumber(method == CommissionMethodEnum.HANDLER ? null : ro.getBankAccountNumber())
                .bankBranch(method == CommissionMethodEnum.HANDLER ? null : ro.getBankBranch())
                .settlementCurrency(method == CommissionMethodEnum.HANDLER ? null : ro.getSettlementCurrency())
                .updateUser(ro.getAccount())
                .updateTime(LocalDateTime.now())
                .build();
    }

    public static ChannelInfoVo entityToVo(ChannelInfo info) {
        return Objects.isNull(info) ? null : ChannelInfoVo.builder()
                .id(info.getId())
                .channelName(info.getChannelName())
                .channelId(info.getChannelId())
                .channelOwner(info.getChannelOwner())
                .contactPhone(info.getContactPhone())
                .commissionRatio(info.getCommissionRatio())
                .commissionMethod(info.getCommissionMethod())
                .commissionPeriod(info.getCommissionPeriod())
                .channelDescription(info.getChannelDescription())
                .bankAccountType(info.getBankAccountType())
                .bankAccountNumber(info.getBankAccountNumber())
                .receivingUnit(info.getReceivingUnit())
                .bankBranch(info.getBankBranch())
                .settlementCurrency(info.getSettlementCurrency())
                .sysInner(info.getSysInner())
                .build();
    }

    public static ChannelInfo buildDefaultChannel(Long orgId, String channelName, Long channelId) {
        return ChannelInfo.builder()
                .orgId(orgId)
                .channelId(channelId)
                .channelName(channelName)
                .channelOwner("-")
                .contactPhone("-")
                .channelDescription("-")
                .commissionRatio(0)
                .commissionMethod(2)
                .sysInner(BoolEnum.TRUE.getCode())
                .createUser(orgId.toString())
                .build();
    }
}
