package kr.ccfc.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 系统菜单
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_menu")
public class SysMenu {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 上级菜单ID
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 上级菜单唯一KEY值
     */
    @TableField(value = "parent_key")
    private String parentKey;

    /**
     * 类型 1-分类 2-节点
     */
    @TableField(value = "`type`")
    private Byte type;

    /**
     * 名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 唯一标识
     */
    @TableField(value = "code")
    private String code;

    /**
     * 描述
     */
    @TableField(value = "`desc`")
    private String desc;

    /**
     * 连接设备 0 PC 和 移动 1 PC 2 移动
     */
    @TableField(value = "device")
    private Integer device;

    /**
     * 目标地址
     */
    @TableField(value = "target_url")
    private String targetUrl;

    /**
     * 排序索引
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 状态 0-无效； 1-有效；
     */
    @TableField(value = "`status`")
    private Byte status;

    /**
     * 创建人
     */
    @TableField(value = "create_by")
    private Long createBy;

    /**
     * 修改人
     */
    @TableField(value = "modify_by")
    private Long modifyBy;

    /**
     * 创建时间
     */
    @TableField(value = "created")
    private Date created;

    /**
     * 用于关联文章
     */
    @TableField(value = "article_type")
    private Integer articleType;

    /**
     * 修改时间
     */
    @TableField(value = "last_update_time")
    private Date lastUpdateTime;

    /**
     * 私密可见
     */
    @TableField(value = "use_secret")
    private Byte useSecret;

    @TableField(value = "use_real_name")
    private Boolean useRealName;

    @TableField(value = "ip_visible")
    private Boolean ipVisible;

    @TableField(value = "file_limit")
    private Integer fileLimit;

    @TableField(value = "max_size")
    private Integer maxSize;

    @TableField(exist = false)
    private List<SysPrivilege> privileges;

    @TableField(exist = false)
    private List<SysMenu> subMenus;

    @TableField(exist = false)
    private Integer articleCount;

    @TableField(exist = false)
    private SysMenu parentMenu;

    @TableField(exist = false)
    private Integer newArticle;

    @TableField(exist = false)
    private Boolean disabled;
}
