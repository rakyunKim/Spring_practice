package kr.ccfc.service;

import kr.ccfc.domain.SysUserStatis;
import com.baomidou.mybatisplus.extension.service.IService;
import kr.ccfc.vo.ChartsVo;

import java.util.Date;

public interface SysUserStatisService extends IService<SysUserStatis> {


    ChartsVo statisToCharts(Date start, Date end);

}




