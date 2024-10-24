package kr.ccfc.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 访问记录
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_user_statis")
public class SysUserStatis {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 访客浏览器
     */
    @TableField(value = "browser")
    private String browser;

    /**
     * 浏览器版本
     */
    @TableField(value = "version")
    private String version;

    /**
     * 浏览器内核
     */
    @TableField(value = "engine")
    private String engine;

    /**
     * 内核版本
     */
    @TableField(value = "engine_version")
    private String engineVersion;

    /**
     * 访客操作系统
     */
    @TableField(value = "os")
    private String os;

    /**
     * 访客ip
     */
    @TableField(value = "remote_host")
    private String remoteHost;

    /**
     * 访问路径
     */
    @TableField(value = "url")
    private String url;

    /**
     * 访客操作系统
     */
    @TableField(value = "plat_form")
    private String platForm;

    /**
     * 访客设备
     */
    @TableField(value = "device")
    private String device;

    /**
     * 访客会员id 0未未登录
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 初次访问时间
     */
    @TableField(value = "created", fill = FieldFill.INSERT)
    private Date created;

    /**
     * 最近访问时间
     */
    @TableField(value = "last_update_time", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateTime;

    @TableField(exist = false)
    private Integer count;

    @TableField(exist = false)
    private String username;

    @TableField(exist = false)
    private String realname;

    public static final String COL_ID = "id";

    public static final String COL_BROWSER = "browser";

    public static final String COL_VERSION = "version";

    public static final String COL_ENGINE = "engine";

    public static final String COL_ENGINE_VERSION = "engine_version";

    public static final String COL_OS = "os";

    public static final String COL_REMOTE_HOST = "remote_host";

    public static final String COL_URL = "url";

    public static final String COL_PLAT_FORM = "plat_form";

    public static final String COL_DEVICE = "device";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_CREATED = "created";

    public static final String COL_LAST_UPDATE_TIME = "last_update_time";
}
