package ag.orca.living.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ErrorCodes {

    SUCCESS(200, "OK"),

    FAIL(-1, "FAIL"),

    PARAM_ERR(400, "PARAM_ERR");


    private final int code;

    private final String msg;

    public static ErrorCodes ofCode(int code) {
        return Arrays.stream(ErrorCodes.values())
                .filter(x -> x.code == code).findFirst().orElse(ErrorCodes.FAIL);
    }
}
