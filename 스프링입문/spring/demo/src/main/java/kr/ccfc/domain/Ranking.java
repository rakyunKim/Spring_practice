package kr.ccfc.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 联赛排名
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ranking")
public class Ranking {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 排名
     */
    @TableField(value = "ranks")
    private Integer ranks;

    /**
     * 赛季开始时间
     */
    @TableField(value = "start_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 赛季结束时间
     */
    @TableField(value = "end_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    /**
     * 俱乐部标志
     */
    @TableField(value = "club_logo_img_url")
    private String clubLogoImgUrl;

    /**
     * 俱乐部名称
     */
    @TableField(value = "club_name")
    private String clubName;

    /**
     * 赛季
     */
    @TableField(value = "season")
    private String season;

    /**
     * 当前比赛场次
     */
    @TableField(value = "current_competition_times")
    private Integer currentCompetitionTimes;

    /**
     * 胜利点数
     */
    @TableField(value = "points")
    private Integer points;

    /**
     * 胜利场次
     */
    @TableField(value = "win")
    private Integer win;

    /**
     * 打平场次
     */
    @TableField(value = "draw")
    private Integer draw;

    /**
     * 失败场次
     */
    @TableField(value = "fail")
    private Integer fail;

    /**
     * 净胜球1
     */
    @TableField(value = "goal_win")
    private Integer goalWin;

    /**
     * 净胜球2
     */
    @TableField(value = "goal_draw")
    private Integer goalDraw;

    /**
     * 净胜球3
     */
    @TableField(value = "goal_fail")
    private Integer goalFail;

    /**
     * 总比赛场次
     */
    @TableField(value = "total_competition_times")
    private Integer totalCompetitionTimes;

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


    /**
     * 主场logo
     */
    @TableField(value = "logo_url")
    private String logoUrl;

    public static final String COL_ID = "id";

    public static final String COL_RANKS = "ranks";

    public static final String COL_START_TIME = "start_time";

    public static final String COL_ENT_TIME = "ent_time";

    public static final String COL_CLUB_LOGO_IMG_URL = "club_logo_img_url";

    public static final String COL_CLUB_NAME = "club_name";

    public static final String COL_SEASON = "season";

    public static final String COL_CURRENT_COMPETITION_TIMES = "current_competition_times";

    public static final String COL_POINTS = "points";

    public static final String COL_WIN = "win";

    public static final String COL_DRAW = "draw";

    public static final String COL_FAIL = "fail";

    public static final String COL_GOAL_WIN = "goal_win";

    public static final String COL_GOAL_DRAW = "goal_draw";

    public static final String COL_GOAL_FAIL = "goal_fail";

    public static final String COL_TOTAL_COMPETITION_TIMES = "total_competition_times";

    public static final String COL_CREATED = "created";

    public static final String COL_LAST_UPDATE_TIME = "last_update_time";
}
