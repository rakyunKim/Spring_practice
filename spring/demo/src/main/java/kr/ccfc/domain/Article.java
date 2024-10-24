package kr.ccfc.domain;

import com.baomidou.mybatisplus.annotation.*;
import kr.ccfc.model.ArticleAttach;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

/**
 * 文章类信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "article")
public class Article {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 标题
     */
    @TableField(value = "title")
    @NotBlank
    private String title;

    /**
     * 简介
     */
    @TableField(value = "description")
    private String description;

    /**
     * 作者
     */
    @TableField(value = "author")
    private String author;

    /**
     * 类型
     */
    @TableField(value = "`type`")
    @NotBlank
    private String type;

    /**
     * 文章状态
     */
    @TableField(value = "`status`")
    private Integer status;

    @TableField(value = "`count`")
    private Integer count;

    /**
     * 文章排序，越大越靠前
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 点击跳转url
     */
    @TableField(value = "url")
    private String url;

    /**
     * 封面图片url
     */
    @TableField(value = "imgUrl")
    private String imgUrl;
    /**
     * 最后修改时间
     */
    @TableField(value = "last_update_time", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateTime;

    /**
     * 创建일期
     */
    @TableField(value = "created", fill = FieldFill.INSERT)
    private Date created;

    /**
     * 坐着用户id
     */
    @TableField(value = "author_user_id")
    private Long authorUserId;

    /**
     * 创建者ip地址
     */
    @TableField(value = "author_ip")
    private String authorIp;

    /**
     * 通知
     */
    @TableField(value = "notice")
    private Boolean notice;

    @TableField(value = "copy_id")
    private Long copyId;

    /**
     * 私密
     */
    @TableField(value = "secret")
    private Boolean secret;

    @TableField(value = "menu_id")
    private Long menuId;

    @TableField(exist = false)
    private Boolean newArticle;

    @TableField(exist = false)
    private SysMenu sysMenu;

    @TableField(exist = false)
    private String target;

    @TableField(exist = false)
    private String targetUrl;

    @TableField(exist = false)
    private List<ArticleAttach> attaches;

    @TableField(exist = false)
    private String menuUrl;
}
