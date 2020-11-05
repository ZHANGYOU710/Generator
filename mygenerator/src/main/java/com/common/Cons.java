package com.common;

/**
 * @author ZhangYou
 * 日 期: 创建时间: 2020/11/05
 * 版 本: v1.0
 * */
public final class Cons {

    private Cons() {
    }

    public static final Integer RESULT_FAIL = 100;
    /**请求成功*/
    public static final Integer RESULT_OK = 1;
    /**请求失败*/
    public static final Integer RESULT_ERR = 0;
    /**数据库中已存在该记录*/
    public static final Integer RECORD_ALREADY_EXISTS = 99;
    /**用户未经授权*/
    public static final Integer USER_UNAUTHORIZED = 401;
    /**用户权限不足*/
    public static final Integer USER_INSUFFICIENT_AUTHORITY = 402;
    /**路径不存在*/
    public static final Integer NO_PATH = 404;
}
