package kr.ccfc.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * 系统弹窗
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_pop_ip")
public class SysPopIp {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 访问者ip地址
     */
    @TableField(value = "ip")
    private String ip;

    /**
     * 弹窗id
     */
    @TableField(value = "sys_pop_id")
    private Long sysPopId;

    /**
     * 当前时间是否展示弹窗
     */
    @TableField(value = "`status`")
    private Byte status;

    public static final String COL_ID = "id";

    public static final String COL_IP = "ip";

    public static final String COL_SYS_POP_ID = "sys_pop_id";

    public static final String COL_STATUS = "status";
}
