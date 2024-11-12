package ag.orca.living.interceptors;

import ag.orca.living.core.auth.AuthInfo;

public class AuthInfoThreadLocal {
    private AuthInfoThreadLocal() {
    }

    private static final ThreadLocal<AuthInfo> LOCAL = new ThreadLocal<>();

    public static void put(AuthInfo info) {
        LOCAL.set(info);
    }

    public static AuthInfo get() {
        return LOCAL.get();
    }

    public static void clear() {
        LOCAL.remove();
    }


}
