package kr.ccfc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import kr.ccfc.domain.SysMenu;
import kr.ccfc.domain.SysRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
    List<SysMenu> queryMenuByRoleIds(@Param("roleIds") List<Long> roleIds);
}
