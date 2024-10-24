package kr.ccfc.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 权限配置
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_privilege")
public class SysPrivilege {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所属菜单Id
     */
    @TableField(value = "menu_id")
    private Long menuId;

    /**
     * 功能点名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 功能描述
     */
    @TableField(value = "description")
    private String description;

    @TableField(value = "url")
    private String url;

    @TableField(value = "`method`")
    private String method;

    /**
     * 创建人
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private Long createBy;

    /**
     * 修改人
     */
    @TableField(value = "modify_by", fill = FieldFill.INSERT_UPDATE)
    private Long modifyBy;

    /**
     * 创建时间
     */
    @TableField(value = "created", fill = FieldFill.INSERT)
    private Date created;

    /**
     * 修改时间
     */
    @TableField(value = "last_update_time", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateTime;

    @TableField(value = "is_system")
    private Byte isSystem;

    public static final String COL_ID = "id";

    public static final String COL_MENU_ID = "menu_id";

    public static final String COL_NAME = "name";

    public static final String COL_DESCRIPTION = "description";

    public static final String COL_URL = "url";

    public static final String COL_METHOD = "method";

    public static final String COL_CREATE_BY = "create_by";

    public static final String COL_MODIFY_BY = "modify_by";

    public static final String COL_CREATED = "created";

    public static final String COL_LAST_UPDATE_TIME = "last_update_time";

    public static final String COL_IS_SYSTEM = "is_system";
}
