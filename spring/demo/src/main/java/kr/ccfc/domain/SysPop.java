package kr.ccfc.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统弹窗
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_pop")
public class SysPop {
    public static final String COL_ENT_TIME = "end_TIme";
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 记录保存时间
     */
    @TableField(value = "refresh_time")
    private Integer refreshTime;

    /**
     * 弹窗开始时间
     */
    @TableField(value = "start_time")
    private Date startTime;

    /**
     * 弹窗结束时间
     */
    @TableField(value = "end_time")
    private Date endTime;

    /**
     * 弹窗左侧位置
     */
    @TableField(value = "`left`")
    private Integer left;

    /**
     * 弹窗顶部位置
     */
    @TableField(value = "`top`")
    private Integer top;

    /**
     * 弹窗宽度
     */
    @TableField(value = "width")
    private Integer width;

    /**
     * 弹窗高度
     */
    @TableField(value = "height")
    private Integer height;

    @TableField(value = "status")
    private Byte status;

    /**
     * 弹窗标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 弹窗内容
     */
    @TableField(value = "content")
    private String content;

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

    public static final String COL_ID = "id";

    public static final String COL_REFRESH_TIME = "refresh_time";

    public static final String COL_START_TIME = "start_time";

    public static final String COL_END_TIME = "end_time";

    public static final String COL_LEFT = "left";

    public static final String COL_TOP = "top";

    public static final String COL_WIDTH = "width";

    public static final String COL_HEIGHT = "height";

    public static final String COL_TITLE = "title";

    public static final String COL_CONTENT = "content";

    public static final String COL_CREATE_BY = "create_by";

    public static final String COL_MODIFY_BY = "modify_by";

    public static final String COL_CREATED = "created";

    public static final String COL_LAST_UPDATE_TIME = "last_update_time";
}
