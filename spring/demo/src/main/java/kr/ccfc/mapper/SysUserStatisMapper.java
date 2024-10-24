package kr.ccfc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import kr.ccfc.domain.SysUserStatis;

import java.util.Date;
import java.util.List;

public interface SysUserStatisMapper extends BaseMapper<SysUserStatis> {
    List<SysUserStatis> statisToCharts(Date ago, Date now);
}
