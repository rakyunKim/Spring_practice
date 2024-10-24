package kr.ccfc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import kr.ccfc.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.management.relation.Role;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import kr.ccfc.mapper.SysRoleMapper;
import kr.ccfc.domain.SysRole;
import kr.ccfc.service.SysRoleService;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public List<SysRole> findByUserId(Serializable id) {
        return sysRoleMapper.findByUserId(id);
    }

    @Override
    public boolean isSysAdmin(Long userId) {
        List<SysRole> sysRoles = sysRoleMapper.queryRoleByUserId(userId);
        for (SysRole sysRole : sysRoles) {
            // 角色包含 ADMIN_ROLE_CODE 时，为超级管理员
            if (Constants.ADMIN_ROLE_CODE.equals(sysRole.getCode())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Integer queryMaxRanks() {
        return sysRoleMapper.queryMaxRanks();
    }

    @Override
    public List<SysRole> queryByMenuId(Long menuId) {
        return sysRoleMapper.queryByMenuId(menuId);
    }

    @Override
    public List<SysRole> queryRoleByMaxRanks(Integer ranks) {
        Integer endRanks = sysRoleService.queryMaxRanks();
        List<Integer> ranksRange = new ArrayList<>();
        for (int i = ranks; i <= endRanks; i++) {
            ranksRange.add(i);
        }
        return list(new LambdaQueryWrapper<SysRole>()
                .in(SysRole::getRanks, ranksRange)
        );
    }
}



