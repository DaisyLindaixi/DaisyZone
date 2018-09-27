package com.daisyZone.core.enumeration;


//import com.hnjme.core.exception.JmeException;

import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据响应枚举
 *
 * @author zhangyizhi 2016年6月16日
 * @since 1.0
 */
public enum ResponseDataEnum {
    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功", Object.class),

    NOTFOUND(404,"记录不存在",Object.class),

    UNAUTHORIZED(401,"用户名或密码错误",Object.class),
    /**
     * 空指针异常
     */
    NULLPOINT(201, "系统内部错误", NullPointerException.class),

    /**
     * 系统内部异常,RuntimeException
     */
    SYSTEM(202, "系统内部错误", RuntimeException.class),

    /**
     * 非法参数异常
     */
    ILLEGALARGUMENT(203, "非法参数错误", IllegalArgumentException.class),

    /**
     * socket异常
     */
    SOCKET(204, "连接错误", SocketException.class),

    /**
     * 系统内部异常,JmeException
     */
//    JME(205, "系统内部错误", JmeException.class),

    /**
     * 系统内部异常,Exception
     */
    OTHER(205, "系统内部错误", Exception.class);

    /**
     * 保存Exception的class与ExceptionEnum对应哈希
     */
    private static Map<Class, ResponseDataEnum> map = new HashMap<>();

    // 遍历所有ExceptionEnum值，将enum对应的Exception作为key，value中放入对应的enum
    static {
        ResponseDataEnum[] values = ResponseDataEnum.values();

        for (ResponseDataEnum e : values) {
            map.put(e.cls, e);
        }
    }

    /**
     * 响应编号
     */
    private int code;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 响应类型
     */
    private Class cls;

    /**
     * 构造方法
     *
     * @param code 响应编号
     * @param msg  响应信息
     * @param cls  响应类型
     */
    ResponseDataEnum(int code, String msg, Class cls) {
        this.code = code;
        this.msg = msg;
        this.cls = cls;
    }

    /**
     * 根据Exception类型，返回不同的异常枚举值
     *
     * @param ex 响应类型
     * @return ResponseDataEnum 异常枚举值
     */
    public static ResponseDataEnum get(Exception ex) {
        ResponseDataEnum exceptionEnum = map.get(ex.getClass());
        if (exceptionEnum == null) {
            return OTHER;
        }

        return exceptionEnum;
    }

    public int getCode() {
        return code;
    }

    public Class getCls() {
        return cls;
    }

    public String getMsg() {
        return msg;
    }
}
