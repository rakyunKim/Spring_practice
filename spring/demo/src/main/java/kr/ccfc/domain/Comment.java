package kr.ccfc.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 评论表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "`comment`")
public class Comment {
    public static final String COL_PRIVATE = "private";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_ARTICLE = "article";
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文章id
     */
    @TableField(value = "article_id")
    private Long articleId;

    /**
     * 上级评论id
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 是否仅自己可见
     */
    @TableField(value = "priv")
    private Boolean priv;

    /**
     * 回复内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 创建人
     */
    @TableField(value = "create_by")
    private Long createBy;

    /**
     * 创建时间
     */
    @TableField(value = "created")
    private Date created;

    /**
     * 修改时间
     */
    @TableField(value = "last_update_time")
    private Date lastUpdateTime;

    @TableField(exist = false)
    private String username;

    @TableField(exist = false)
    private String realName;

    public static final String COL_ID = "id";

    public static final String COL_ARTICLE_ID = "article_id";

    public static final String COL_PARENT_ID = "parent_id";

    public static final String COL_PRIV = "priv";

    public static final String COL_CONTENT = "content";

    public static final String COL_CREATE_BY = "create_by";

    public static final String COL_CREATED = "created";

    public static final String COL_LAST_UPDATE_TIME = "last_update_time";
}
