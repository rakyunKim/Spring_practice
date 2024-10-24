package kr.ccfc.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import kr.ccfc.mapper.SysRolePrivilegeUserMapper;
import kr.ccfc.domain.SysRolePrivilegeUser;
import kr.ccfc.service.SysRolePrivilegeUserService;
@Service
public class SysRolePrivilegeUserServiceImpl extends ServiceImpl<SysRolePrivilegeUserMapper, SysRolePrivilegeUser> implements SysRolePrivilegeUserService{

}
