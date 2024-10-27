package kr.ccfc.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 用户表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_user")
@Builder
public class SysUser {
    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户类型：1-普通用户
     */
    @TableField(value = "`type`")
    private Integer type;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 座机
     */
    @TableField(value = "landline")
    private String landline;

    /**
     * 手机号
     */
    @TableField(value = "mobile")
    private String mobile;

    /**
     * 邮编
     */
    @TableField(value = "post_code")
    private String postCode;

    /**
     * 基本地址
     */
    @TableField(value = "base_address")
    private String baseAddress;

    /**
     * 详细地址
     */
    @TableField(value = "detail_address")
    private String detailAddress;

    /**
     * 密码
     */
    @TableField(value = "`password`")
    private String password;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 真实姓名
     */
    @TableField(value = "real_name")
    private String realName;

    /**
     * 昵称
     */
    @TableField(value = "nick_name")
    private String nickName;

    /**
     * 主页
     */
    @TableField(value = "home_page")
    private String homePage;

    /**
     * 身份证号
     */
    @TableField(value = "id_card")
    private String idCard;

    /**
     * 登录数
     */
    @TableField(value = "logins")
    private Integer logins;

    /**
     * 最后登录일期
     */
    @TableField(value = "last_visit_time")
    private Date lastVisitTime;

    /**
     * 权限级别
     */
    @TableField(value = "privilege_ranks")
    private Integer privilegeRanks;

    /**
     * 是否接收短信
     */
    @TableField(value = "receive_msg")
    private Byte receiveMsg;

    /**
     * 会员申请存在
     */
    @TableField(value = "member_apply")
    private Byte memberApply;

    /**
     * 状态：0，禁用；1，启用；
     */
    @TableField(value = "`status`")
    private Byte status;

    /**
     * 赞助金
     */
    @TableField(value = "praise_grant")
    private String praiseGrant;

    /**
     * 修改时间
     */
    @TableField(value = "last_update_time")
    private Date lastUpdateTime;

    /**
     * 创建时间
     */
    @TableField(value = "created")
    private Date created;

    /**
     * 是否接收邮件
     */
    @TableField(value = "receive_email")
    private Byte receiveEmail;

    /**
     * 股金
     */
    @TableField(value = "stock_fund")
    private String stockFund;

    /**
     * 角色数据
     */
    @TableField(exist = false)
    private List<SysRole> roles;
    /**
     * 角色id
     */
    @TableField(exist = false)
    private Long roleId;

    @TableField("fb_id")
    private Long fbId;

    @TableField("ka_kao_id")
    private Long kaKaoId;

    @TableField("naver_id")
    private String naverId;

    @TableField("orig_pwd")
    private String origPwd;
}
