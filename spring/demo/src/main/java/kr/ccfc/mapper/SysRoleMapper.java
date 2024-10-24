package kr.ccfc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import kr.ccfc.domain.SysRole;

import javax.management.relation.Role;
import java.io.Serializable;
import java.util.List;

public interface SysRoleMapper extends BaseMapper<SysRole> {
    List<SysRole> findByUserId(Serializable id);

    List<SysRole> queryRoleByUserId(Long userId);

    Integer queryMaxRanks();

    List<SysRole> queryByMenuId(Long menuId);
}
