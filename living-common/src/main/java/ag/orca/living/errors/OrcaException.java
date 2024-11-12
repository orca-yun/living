package ag.orca.living.errors;

import lombok.Getter;

@Getter
public class OrcaException extends RuntimeException {

    private final int code;

    public OrcaException() {
        this(ErrorCodes.FAIL.getMsg());
    }

    public OrcaException(String msg) {
        this(ErrorCodes.FAIL.getCode(), msg);
    }

    public OrcaException(int code, String msg) {
        this(code, msg, null);
    }

    public OrcaException(int code, String msg, Throwable e) {
        super(msg, e);
        this.code = code;
    }

    public static OrcaException error(String msg) {
        return new OrcaException(msg);
    }

    public static OrcaException error(int code, String msg) {
        return new OrcaException(code, msg);
    }

    public static OrcaException error(String msg, Throwable e) {
        return new OrcaException(ErrorCodes.FAIL.getCode(), msg, e);
    }


}
