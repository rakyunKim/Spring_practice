package kr.ccfc.service.impl;

import kr.ccfc.domain.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import kr.ccfc.domain.SysRoleMenu;
import kr.ccfc.mapper.SysRoleMenuMapper;
import kr.ccfc.service.SysRoleMenuService;

@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<SysMenu> queryMenuByRoleIds(List<Long> roleIds) {
        return sysRoleMenuMapper.queryMenuByRoleIds(roleIds);
    }
}
