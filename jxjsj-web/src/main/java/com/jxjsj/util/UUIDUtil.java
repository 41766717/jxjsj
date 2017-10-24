package com.jxjsj.util;

import java.util.UUID;

/**
 * Created by niyang on 2017/10/24.
 */
public class UUIDUtil {

    /**
     * 生成没有横线，且所有字母大写的UUID
     * 形如：<p>CE3AA96952A34ED499B4FF8913D685B9</p>
     * @return
     */
    public static String generateUUID(){
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }
}
