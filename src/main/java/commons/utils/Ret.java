package commons.utils;

import java.util.HashMap;

import static commons.utils.StatusCode.FORBIDDEN;
import static commons.utils.StatusCode.UNAUTHORIZED;

/**
 * @author guorui1
 */
public class Ret extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    public static final int SC_OK = 0;
    public static final int SC_ERROR = -1;

    public Ret(int code, String msg) {
        this.set("code", code).set("msg", msg);
    }

    public static Ret error(String msg) {
        return new Ret(SC_ERROR, msg);
    }

    public static Ret ok() {
        return Ret.ok("success");
    }

    public static Ret ok(String msg) {
        return new Ret(SC_OK, msg);
    }

    public Ret set(String key, Object value) {
        this.put(key, value);
        return this;
    }

    public Ret data(Object obj) {
        super.put("data", obj);
        return this;
    }

    public static Ret unauthorized() {
        return new Ret(UNAUTHORIZED.getCode(), UNAUTHORIZED.getMsg());
    }

    public static Ret forbidden() {
        return new Ret(FORBIDDEN.getCode(), FORBIDDEN.getMsg());
    }
}