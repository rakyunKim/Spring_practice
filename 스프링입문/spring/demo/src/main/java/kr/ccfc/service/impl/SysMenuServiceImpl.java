package kr.ccfc.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import kr.ccfc.base.BaseUtil;
import kr.ccfc.domain.*;
import kr.ccfc.mapper.SysMenuMapper;
import kr.ccfc.model.MenuForm;
import kr.ccfc.model.PrivilegesFrom;
import kr.ccfc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysPrivilegeService sysPrivilegeService;

    @Autowired
    private SysRolePrivilegeService sysRolePrivilegeService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    private final Map<String, String> menuPrivileges = new HashMap<>();

    @PostConstruct
    public void init() {
        menuPrivileges.put("_list", "列表查看权限");
        menuPrivileges.put("_read", "读取权限");
        menuPrivileges.put("_write", "写权限");
        menuPrivileges.put("_replay", "回复权限");
        menuPrivileges.put("_comment", "评论权限");
        menuPrivileges.put("_url", "链接权限");
        menuPrivileges.put("_upload", "上传权限");
        menuPrivileges.put("_download", "下载权限");
    }

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> getMenuByUserId(Long userId) {
        boolean sysAdmin = sysRoleService.isSysAdmin(userId);
        if (sysAdmin) {
            // 用户是超级管理员 拥有所有的权限数据
            List<SysMenu> list = list(new LambdaQueryWrapper<SysMenu>()
                    .eq(SysMenu::getId, 1)
            );
            // 拿到菜单下的所有权限数据
            for (SysMenu sysMenu : list) {
                List<SysPrivilege> privileges = sysPrivilegeService.list(new LambdaQueryWrapper<SysPrivilege>()
                        .eq(SysPrivilege::getMenuId, sysMenu.getId())
                );
                sysMenu.setPrivileges(privileges);
            }
            return list;
        } else {
            // 拿到用户的最高角色数据  再根据角色数据拿到所有的权限数据
            List<SysRole> userRoles = sysRoleService.findByUserId(userId);
            // 获取级别最高的角色，拿到角色包含的权限
            SysRole sysRole = userRoles.stream().min(Comparator.comparingInt(SysRole::getRanks)).get();
            // 拿到用户拥有的角色列表
            Integer ranks = sysRole.getRanks();
            List<SysRole> roles = sysRoleService.queryRoleByMaxRanks(ranks);
            // 根据用户所拥有的的所有角色  拿到菜单数据
            List<Long> roleIds = roles.stream().map(SysRole::getId).collect(Collectors.toList());
            List<SysMenu> sysMenus = sysRoleMenuService.queryMenuByRoleIds(roleIds);
            // 查询角色所有的权限 根据权限等级
            List<SysPrivilege> privileges = sysPrivilegeService.queryByRoleRanks(sysRole.getRanks());
            Map<Long, List<SysPrivilege>> menuIdPrivilegesList = privileges.stream().collect(Collectors.groupingBy(SysPrivilege::getMenuId));
            for (SysMenu sysMenu : sysMenus) {
                // 先拿到用户的角色  再根据角色和menuId 拿到所有的权限数据
                List<SysPrivilege> sysPrivileges = menuIdPrivilegesList.get(sysMenu.getId());
                sysMenu.setPrivileges(sysPrivileges);
            }
            return sysMenus;
        }
    }

    /**
     * 创建菜单并添加菜单权限
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveWithPrivileges(MenuForm menuForm) {
        SysMenu sysMenu = menuForm.getMenu();
        if (sysMenu.getId() != null) {
            updateById(sysMenu);
            // 删除权限关联数据
            List<SysPrivilege> list = sysPrivilegeService.list(new LambdaQueryWrapper<SysPrivilege>()
                    .eq(SysPrivilege::getMenuId, sysMenu.getId())
            );
            List<Long> idList = list.stream().map(SysPrivilege::getId).collect(Collectors.toList());
            sysPrivilegeService.remove(new LambdaQueryWrapper<SysPrivilege>()
                    .eq(SysPrivilege::getMenuId, sysMenu.getId())
            );
            sysRolePrivilegeService.remove(new LambdaQueryWrapper<SysRolePrivilege>()
                    .in(SysRolePrivilege::getPrivilegeId, idList)
            );
            sysRoleMenuService.remove(new LambdaQueryWrapper<SysRoleMenu>()
                    .eq(SysRoleMenu::getMenuId, sysMenu.getId())
            );
        } else {
            sysMenu.setCode(RandomUtil.randomString(5));
            boolean save = save(sysMenu);
        }
        // 拿到权限的配置数据
        PrivilegesFrom privileges = menuForm.getPrivileges();
        Map<String, Long> privilegesRole = new HashMap<>();
        privilegesRole.put("_list", privileges.getList());
        privilegesRole.put("_read", privileges.getRead());
        privilegesRole.put("_write", privileges.getWrite());
        privilegesRole.put("_replay", privileges.getReplay());
        privilegesRole.put("_comment", privileges.getComment());
        privilegesRole.put("_url", privileges.getUrl());
        privilegesRole.put("_upload", privileges.getUpload());
        privilegesRole.put("_download", privileges.getDownload());

        for (String menuPrivilege : menuPrivileges.keySet()) {
            SysPrivilege sysPrivilege = new SysPrivilege();
            sysPrivilege.setMenuId(sysMenu.getId());
            String[] split = sysMenu.getTargetUrl().split("/");
            sysPrivilege.setName(split[split.length - 1] + menuPrivilege);
            sysPrivilege.setDescription(menuPrivileges.get(menuPrivilege));
            sysPrivilege.setUrl(sysMenu.getTargetUrl());
            sysPrivilegeService.save(sysPrivilege);

            // 绑定角色权限数据
            SysRolePrivilege sysRolePrivilege = new SysRolePrivilege();
            sysRolePrivilege.setPrivilegeId(sysPrivilege.getId());
            sysRolePrivilege.setRoleId(privilegesRole.get(menuPrivilege));
            sysRolePrivilegeService.save(sysRolePrivilege);
        }

        // 列表查看权限作为菜单权限
        Long list = privileges.getList();
        SysRoleMenu sysRoleMenu = new SysRoleMenu();
        sysRoleMenu.setMenuId(sysMenu.getId());
        sysRoleMenu.setRoleId(list);
        sysRoleMenuService.save(sysRoleMenu);
        return true;
    }

    @Override
    public MenuForm getSysMenuAndPrivilegesById(Long id) {
        MenuForm menuForm = new MenuForm();
        SysMenu sysMenu = getById(id);
        menuForm.setMenu(sysMenu);
        PrivilegesFrom privilegesFrom = new PrivilegesFrom();
        // 查询菜单归属的角色等级
        List<SysRole> roles = sysRoleService.queryByMenuId(id);
        Map<String, List<SysRole>> privilegeNameRoleMap = roles.stream().collect(Collectors.groupingBy(SysRole::getPrivilegeName));
        for (String privilegeName : privilegeNameRoleMap.keySet()) {
            String[] privileges = privilegeName.split("_");
            String privilege = privileges[privileges.length - 1];
            List<SysRole> roleList
                    = privilegeNameRoleMap.get(privilegeName);
            if (!CollectionUtils.isEmpty(roleList)) {
                SysRole sysRole = roleList.get(0);
                Long roleId = sysRole.getId();
                generatePrivilegesForm(privilege, roleId, privilegesFrom);
            }

        }
        menuForm.setPrivileges(privilegesFrom);
        return menuForm;
    }

    /**
     * 登录后初始化菜单
     */
    @Override
    public List<SysMenu> initMenus(Long userId) {
        // 先拿到所有的根菜单
        List<SysMenu> rootMenus = sysMenuService.list(new LambdaQueryWrapper<SysMenu>()
                .eq(SysMenu::getParentId, 0L)
                .eq(SysMenu::getStatus, 1)
        );
        List<SysMenu> subMenus = this.getMenuByUserId(userId);
        getSubMenus(rootMenus, subMenus);
        return rootMenus;
    }


    @Override
    public List<SysMenu> getNoLoginMenus() {
        // 先拿到所有的根菜单
        List<SysMenu> rootMenus = sysMenuService.list(new LambdaQueryWrapper<SysMenu>()
                .eq(SysMenu::getParentId, 0L)
                .eq(SysMenu::getStatus, 1)
        );
        SysRole noLoginRole = sysRoleService.getOne(new LambdaQueryWrapper<SysRole>()
                .eq(SysRole::getRanks, 10)
        );
        List<SysMenu> menusByRole = getMenusByRole(noLoginRole.getId());
        getSubMenus(rootMenus, menusByRole);
        return rootMenus;
    }

    private void getSubMenus(List<SysMenu> rootMenus, List<SysMenu> menusByRole) {
        Map<Long, List<SysMenu>> noLoginMenus = menusByRole.stream().collect(Collectors.groupingBy(SysMenu::getParentId));
        for (SysMenu rootMenu : rootMenus) {
            List<SysMenu> sysMenus = noLoginMenus.get(rootMenu.getId());
            if (CollectionUtils.isEmpty(sysMenus)) {
                sysMenus = Collections.emptyList();
            }
            rootMenu.setSubMenus(sysMenus);
        }
    }

    @Override
    public List<SysMenu> getMenusByRole(Long roleId) {
        SysRole sysRole = sysRoleService.getById(roleId);
        Integer ranks = sysRole.getRanks();
        List<SysRole> roles = sysRoleService.queryRoleByMaxRanks(ranks);
        List<Long> roleIds = roles.stream().map(SysRole::getId).collect(Collectors.toList());
        List<SysMenu> sysMenus = sysRoleMenuService.queryMenuByRoleIds(roleIds);
        // 获取 当前role 下的权限数据
        List<SysPrivilege> privileges = sysPrivilegeService.queryByRoleRanks(sysRole.getRanks());
        Map<Long, List<SysPrivilege>> menuIdPrivilegesList = privileges.stream().collect(Collectors.groupingBy(SysPrivilege::getMenuId));
        for (SysMenu sysMenu : sysMenus) {
            // 先拿到用户的角色  再根据角色和menuId 拿到所有的权限数据
            List<SysPrivilege> sysPrivileges = menuIdPrivilegesList.get(sysMenu.getId());
            sysMenu.setPrivileges(sysPrivileges);
        }
        return sysMenus;
    }

    @Override
    public void changeStatus(Long menuId, Byte status) {
        SysMenu menu = getById(menuId);
        menu.setStatus(status);
        updateById(menu);
    }

    @Override
    public List<SysMenu> menuTrees() {
        // 先拿到所有的根菜单
        List<SysMenu> rootMenus = sysMenuService.list(new LambdaQueryWrapper<SysMenu>()
                .eq(SysMenu::getParentId, 0L)
        );
        for (SysMenu rootMenu : rootMenus) {
            rootMenu.setDisabled(true);
        }
        List<SysMenu> subMenus = this.getMenuByUserId(BaseUtil.getUserId());
        for (SysMenu subMenu : subMenus) {
            String targetUrl = subMenu.getTargetUrl();
            if (!targetUrl.contains("_list")) {
                subMenu.setDisabled(true);
            }
        }
        getSubMenus(rootMenus, subMenus);
        return rootMenus;
    }

    private void generatePrivilegesForm(String privilege, Long roleId, PrivilegesFrom privilegesFrom) {
        switch (privilege) {
            case "list":
                privilegesFrom.setList(roleId);
                break;
            case "read":
                privilegesFrom.setRead(roleId);
                break;
            case "write":
                privilegesFrom.setWrite(roleId);
                break;
            case "replay":
                privilegesFrom.setReplay(roleId);
                break;
            case "comment":
                privilegesFrom.setComment(roleId);
                break;
            case "url":
                privilegesFrom.setUrl(roleId);
                break;
            case "upload":
                privilegesFrom.setUpload(roleId);
                break;
            case "download":
                privilegesFrom.setDownload(roleId);
                break;
            default:
                break;
        }
    }
}


