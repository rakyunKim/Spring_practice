package kr.ccfc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import kr.ccfc.domain.SysPrivilege;
import kr.ccfc.mapper.SysPrivilegeMapper;
import kr.ccfc.service.SysPrivilegeService;
import kr.ccfc.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysPrivilegeServiceImpl extends ServiceImpl<SysPrivilegeMapper, SysPrivilege> implements SysPrivilegeService {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysPrivilegeMapper sysPrivilegeMapper;

    @Override
    public List<SysPrivilege> queryByRoleRanks(Integer ranks) {
        Integer endRanks = sysRoleService.queryMaxRanks();
        List<Integer> ranksRange = new ArrayList<>();
        for (int i = ranks; i <= endRanks; i++) {
            ranksRange.add(i);
        }
        return sysPrivilegeMapper.queryByRoleRanks(ranksRange);
    }

}
