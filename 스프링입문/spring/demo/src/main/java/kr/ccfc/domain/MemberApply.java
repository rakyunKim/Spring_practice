package kr.ccfc.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 赞助商注册申请
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "member_apply")
public class MemberApply {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "user_id")
    private Long userId;

    /**
     * 登记号码
     */
    @TableField(value = "register_no")
    private String registerNo;

    /**
     * 公司名称
     */
    @TableField(value = "company_name")
    private String companyName;

    /**
     * 座机
     */
    @TableField(value = "landline")
    private String landline;

    /**
     * 传真
     */
    @TableField(value = "fax")
    private String fax;

    /**
     * 手机号码
     */
    @TableField(value = "mobile")
    private String mobile;

    /**
     * 邮箱地址
     */
    @TableField(value = "email")
    private String email;

    /**
     * 位置
     */
    @TableField(value = "address")
    private String address;

    @TableField(value = "detail_address")
    private String detailAddress;
    /**
     * 注册方式
     */
    @TableField(value = "register_type")
    private Boolean registerType;

    /**
     * 账户持有人
     */
    @TableField(value = "holder_name")
    private String holderName;

    /**
     * 账户持有人出生일期
     */
    @TableField(value = "holder_birth")
    private String holderBirth;

    /**
     * 银行卡名称
     */
    @TableField(value = "bank_name")
    private String bankName;

    /**
     * 账号
     */
    @TableField(value = "account")
    private String account;

    @TableField(value = "cooperator")
    private String cooperator;

    @TableField(value = "cooperator_money")
    private String cooperatorMoney;

    /**
     * 每월支付
     */
    @TableField(value = "month_pay")
    private Integer monthPay;

    /**
     * 1 每월自动扣款 2 直接存款
     */
    @TableField(value = "pay_type")
    private Byte payType;

    /**
     * 付款일期
     */
    @TableField(value = "pay_date")
    private String payDate;

    /**
     * 缴款金额
     */
    @TableField(value = "pay_money")
    private Long payMoney;

    /**
     * 指定业务 0 选中 1 不选中
     */
    @TableField(value = "service")
    private Boolean service;

    /**
     * 其他业务 0 选中 1 不选中
     */
    @TableField(value = "check_business")
    private Boolean checkBusiness;

    /**
     * 转账方式
     */
    @TableField(value = "check_transfer")
    private Boolean checkTransfer;

    /**
     * 其他业务
     */
    @TableField(value = "business")
    private String business;

    /**
     * yonghuming
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 其他业务 0 不同意 1 同意
     */
    @TableField(value = "info_collect")
    private Boolean infoCollect;

    /**
     * 向第三方提供个人信息 0 不同意 1 同意
     */
    @TableField(value = "third_provide")
    private Boolean thirdProvide;

    @TableField(value = "post_code")
    private String postCode;

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

}
