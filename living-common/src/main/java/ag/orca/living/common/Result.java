package ag.orca.living.common;

import ag.orca.living.errors.ErrorCodes;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@SuperBuilder
@Data
@NoArgsConstructor
public abstract class Result implements Serializable {

    @Builder.Default
    private int code = ErrorCodes.FAIL.getCode();

    private String msg;

}
