package kr.ccfc.service;

import kr.ccfc.domain.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import kr.ccfc.model.MenuForm;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {


    List<SysMenu> getMenuByUserId(Long userId);

    /**
     * 创建公告板
     * 同时创建公告板的权限数据
     */
    boolean saveWithPrivileges(MenuForm sysMenu);

    MenuForm getSysMenuAndPrivilegesById(Long id);

    List<SysMenu> initMenus(Long userId);

    List<SysMenu> getNoLoginMenus();

    List<SysMenu> getMenusByRole(Long roleId);

    void changeStatus(Long menuId, Byte status);

    List<SysMenu> menuTrees();
}


