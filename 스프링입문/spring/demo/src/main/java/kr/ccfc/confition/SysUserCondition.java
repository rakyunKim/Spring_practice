package kr.ccfc.confition;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import kr.ccfc.domain.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserCondition extends Page<SysUser> {
    private String username;
    private String nickName;
    private String realName;
    private Long roleId;
    private String email;
    private String mobile;
    private String landline;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date created;
    private String createdStr;
    private String ip;
}
