package kr.ccfc.service;

import kr.ccfc.domain.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.management.relation.Role;
import java.io.Serializable;
import java.util.List;

public interface SysRoleService extends IService<SysRole> {


    List<SysRole> findByUserId(Serializable id);

    boolean isSysAdmin(Long userId);

    Integer queryMaxRanks();

    List<SysRole> queryByMenuId(Long id);

    List<SysRole> queryRoleByMaxRanks(Integer ranks);
}


