package ag.orca.living.common;

import ag.orca.living.errors.ErrorCodes;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class OrcaPageResult<T> extends Result {

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Long total;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private List<T> data;

    public static <T> OrcaPageResult<T> success(Long total, List<T> data) {
        return success(ErrorCodes.SUCCESS.getCode(), ErrorCodes.SUCCESS.getMsg(), total, data);
    }

    public static <T> OrcaPageResult<T> success(int code, String msg, Long total, List<T> data) {
        return OrcaPageResult.<T>builder().code(code).msg(msg).total(total).data(data).build();
    }
}
