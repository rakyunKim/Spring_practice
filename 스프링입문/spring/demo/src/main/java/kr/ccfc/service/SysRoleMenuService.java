package kr.ccfc.service;

import kr.ccfc.domain.SysMenu;
import kr.ccfc.domain.SysRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysRoleMenuService extends IService<SysRoleMenu> {


    List<SysMenu> queryMenuByRoleIds(List<Long> roleIds);
}
