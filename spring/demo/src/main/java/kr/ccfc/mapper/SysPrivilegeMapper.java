package kr.ccfc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import kr.ccfc.domain.SysPrivilege;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysPrivilegeMapper extends BaseMapper<SysPrivilege> {

    List<SysPrivilege> queryByRoleRanks(@Param("ranksRange") List<Integer> ranksRange);
}
