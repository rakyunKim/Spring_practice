package kr.ccfc.service;

import kr.ccfc.domain.SysPrivilege;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysPrivilegeService extends IService<SysPrivilege> {


    List<SysPrivilege> queryByRoleRanks(Integer ranks);
}
