package kr.ccfc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import kr.ccfc.domain.SysUser;
import kr.ccfc.model.LoginResult;
import kr.ccfc.vo.ChartsVo;

import java.util.Date;

public interface SysUserService extends IService<SysUser> {


    /**
     * 用户登录
     */
    LoginResult login(String username, String password);


    /**
     * 注册
     */
    boolean register(SysUser user);


    /**
     * 列表查询
     */
    Page<SysUser> pageQuery(Page<SysUser> page);

    boolean saveSysUser(SysUser sysUser);

    ChartsVo statisToCharts(Date start, Date end);


    Boolean sendResetEmail(String username, String email);

    boolean resetConfirm(String username, String email, String code, String newPwd);

    boolean sendIdFindEmail(String realName, String email);
}

