package kr.ccfc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import kr.ccfc.domain.SysUser;

import java.util.List;

public interface SysUserMapper extends BaseMapper<SysUser> {

    List<SysUser> pageQuery(Page<SysUser> page);
}
