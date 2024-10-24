package kr.ccfc.service;

import kr.ccfc.domain.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SysUserRoleService extends IService<SysUserRole> {


    void bindUserRole(Long userId, Long roleId);
}


