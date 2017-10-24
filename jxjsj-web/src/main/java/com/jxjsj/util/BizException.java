package com.jxjsj.util;


/**
 * Created by niyang on 2017/8/15.
 */
public class BizException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private Integer code = null;
    private String msg = null;

    public BizException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }


    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public static void fail(Integer code, String msg) {
        throw new BizException(code, msg);
    }

    public static void isNull(Object obj, String msg) {
        if(obj == null || obj.equals("")) {
            throw new BizException(603, msg + "不能为空");
        }
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}


