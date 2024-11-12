package ag.orca.living.common;

import ag.orca.living.errors.ErrorCodes;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class OrcaResult<T> extends Result {


    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private T data;

    public static OrcaResult<Void> success() {
        return success(ErrorCodes.SUCCESS.getCode(), ErrorCodes.SUCCESS.getMsg(), null);
    }

    public static <T> OrcaResult<T> success(int code, String msg, T data) {
        return OrcaResult.<T>builder().code(code).msg(msg).data(data).build();
    }

    public static <T> OrcaResult<T> success(T data) {
        return success(ErrorCodes.SUCCESS.getCode(), ErrorCodes.SUCCESS.getMsg(), data);
    }

    public static OrcaResult<Void> fail(int code, String msg) {
        return OrcaResult.<Void>builder().code(code).msg(msg).build();
    }

}
