package kr.ccfc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import kr.ccfc.domain.SysMenu;

import java.util.List;

public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * 查询用户的菜单权限
     */
    List<SysMenu> queryMenuByUserId(Long userId);
}
