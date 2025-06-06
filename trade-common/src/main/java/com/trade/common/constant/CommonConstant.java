package com.trade.common.constant;

/**
 * <p>通用常量类</p>
 *
 * @author creator
 * @since 2024-01-01
 */
public final class CommonConstant {

    private CommonConstant() {
        // 私有构造函数，防止实例化
    }

    // --------------------------- 符号常量 ---------------------------
    public static final String EMPTY_STRING = "";
    public static final String SPACE = " ";
    public static final String COMMA = ",";
    public static final String PERIOD = ".";
    public static final String COLON = ":";
    public static final String SEMICOLON = ";";
    public static final String HYPHEN = "-";
    public static final String UNDERSCORE = "_";
    public static final String SLASH = "/";
    public static final String BACKSLASH = "\\";
    public static final String PIPE = "|";

    // --------------------------- HTTP 相关常量 ---------------------------
    /**
     * HTTP 请求头：认证 (Authorization)
     */
    public static final String HTTP_HEADER_AUTHORIZATION = "Authorization";
    /**
     * HTTP 请求头：Bearer Token 前缀
     */
    public static final String HTTP_HEADER_BEARER_PREFIX = "Bearer ";
    /**
     * HTTP 请求头：内容类型 (Content-Type)
     */
    public static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";
    /**
     * HTTP 内容类型：JSON
     */
    public static final String CONTENT_TYPE_JSON = "application/json;charset=UTF-8";
    /**
     * HTTP 内容类型：表单
     */
    public static final String CONTENT_TYPE_FORM_URLENCODED = "application/x-www-form-urlencoded;charset=UTF-8";
    /**
     * HTTP 内容类型：文件上传
     */
    public static final String CONTENT_TYPE_MULTIPART_FORM_DATA = "multipart/form-data";

    // --------------------------- 编码常量 ---------------------------
    public static final String CHARSET_UTF8 = "UTF-8";
    public static final String CHARSET_GBK = "GBK";

    // --------------------------- 日期时间格式常量 ---------------------------
    public static final String DATETIME_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd";
    public static final String TIME_FORMAT_DEFAULT = "HH:mm:ss";
    public static final String DATETIME_FORMAT_NO_SEPARATOR = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT_NO_SEPARATOR = "yyyyMMdd";

    // --------------------------- 缓存相关常量 ---------------------------
    /**
     * 缓存键分隔符
     */
    public static final String CACHE_KEY_SEPARATOR = "::";
    /**
     * 用户信息缓存键前缀
     */
    public static final String CACHE_USER_PREFIX = "user";
    /**
     * Token 缓存键前缀
     */
    public static final String CACHE_TOKEN_PREFIX = "token";

    // --------------------------- 逻辑删除状态 ---------------------------
    /**
     * 逻辑未删除
     */
    public static final Integer LOGIC_NOT_DELETED = 0;
    /**
     * 逻辑已删除
     */
    public static final Integer LOGIC_DELETED = 1;

    // --------------------------- 通用状态 (启用/禁用) ---------------------------
    /**
     * 状态：启用
     */
    public static final Integer STATUS_ENABLED = 1;
    /**
     * 状态：禁用
     */
    public static final Integer STATUS_DISABLED = 0;

    // --------------------------- 默认值常量 ---------------------------
    /**
     * 默认分页大小
     */
    public static final int DEFAULT_PAGE_SIZE = 10;
    /**
     * 默认当前页码
     */
    public static final int DEFAULT_PAGE_NUM = 1;

    // --------------------------- 其他常量 ---------------------------
    /**
     * 默认的超级管理员用户ID (示例)
     */
    public static final Long SUPER_ADMIN_ID = 1L;
    /**
     * 默认的超级管理员角色代码 (示例)
     */
    public static final String ROLE_SUPER_ADMIN = "ROLE_SUPER_ADMIN";

}