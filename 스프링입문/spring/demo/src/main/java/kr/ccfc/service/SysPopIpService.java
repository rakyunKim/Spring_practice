package kr.ccfc.service;

import kr.ccfc.domain.SysPop;
import kr.ccfc.domain.SysPopIp;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysPopIpService extends IService<SysPopIp>{


    List<SysPop> getPopList(String remoteAddr);

    void modifyPopStatus(Long popId, Integer status);

    void modifyVisitorStatus(Long id, Integer status);
}
