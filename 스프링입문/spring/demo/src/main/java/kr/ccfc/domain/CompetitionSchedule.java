package kr.ccfc.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 比赛计划
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "competition_schedule")
public class CompetitionSchedule {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 比赛时间
     */
    @TableField(value = "date_time")
    private Date dateTime;

    /**
     * 比赛地点
     */
    @TableField(value = "place")
    private String place;

    /**
     * 赛季
     */
    @TableField(value = "season")
    private String season;

    /**
     * 赛季logo
     */
    @TableField(value = "season_img_url")
    private String seasonImgUrl;

    /**
     * 主场
     */
    @TableField(value = "home_team")
    private String homeTeam;

    /**
     * 客场
     */
    @TableField(value = "visit_team")
    private String visitTeam;

    /**
     * 主场logo
     */
    @TableField(value = "home_team_img_url")
    private String homeTeamImgUrl;

    /**
     * 客场logo
     */
    @TableField(value = "visit_team_img_url")
    private String visitTeamImgUrl;

    /**
     * 主队得分
     */
    @TableField(value = "home_scope")
    private Integer homeScope;

    /**
     * 客队得分
     */
    @TableField(value = "visit_scope")
    private Integer visitScope;

    /**
     * 初次访问时间
     */
    @TableField(value = "created")
    private Date created;

    /**
     * 最近访问时间
     */
    @TableField(value = "last_update_time")
    private Date lastUpdateTime;

    /**
     * 是否展示
     */
    @TableField(exist = false)
    private Boolean show;

    public static final String COL_ID = "id";

    public static final String COL_DATE_TIME = "date_time";

    public static final String COL_PLACE = "place";

    public static final String COL_SEASON = "season";

    public static final String COL_SEASON_IMG_URL = "season_img_url";

    public static final String COL_HOME_TEAM = "home_team";

    public static final String COL_VISIT_TEAM = "visit_team";

    public static final String COL_HOME_TEAM_IMG_URL = "home_team_img_url";

    public static final String COL_VISIT_TEAM_IMG_URL = "visit_team_img_url";

    public static final String COL_HOME_SCOPE = "home_scope";

    public static final String COL_VISIT_SCOPE = "visit_scope";

    public static final String COL_CREATED = "created";

    public static final String COL_LAST_UPDATE_TIME = "last_update_time";
    @TableField(exist = false)
    private String time;
    @TableField(exist = false)
    private String day;
    @TableField(exist = false)
    private String week;
}
