package kr.ccfc.service.impl;

import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import kr.ccfc.base.BaseUtil;
import kr.ccfc.domain.SysPop;
import kr.ccfc.domain.SysPopIp;
import kr.ccfc.mapper.SysPopIpMapper;
import kr.ccfc.service.SysPopIpService;
import kr.ccfc.service.SysPopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SysPopIpServiceImpl extends ServiceImpl<SysPopIpMapper, SysPopIp> implements SysPopIpService {

    @Autowired
    private SysPopService sysPopService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public List<SysPop> getPopList(String remote) {
        Date date = new Date();
        List<SysPop> list = sysPopService.list(
                new LambdaQueryWrapper<SysPop>()
                        .eq(SysPop::getStatus, 1)
                        .lt(SysPop::getStartTime, date)
                        .gt(SysPop::getEndTime, date)
        );
        Iterator<SysPop> iterator = list.iterator();
        while (iterator.hasNext()) {
            SysPop sysPop = iterator.next();
            Boolean exist = stringRedisTemplate.hasKey("SYSPOP:" + sysPop.getId() + ":" + remote);
            if (BooleanUtil.isTrue(exist)) {
                iterator.remove();
            }
        }
        return list;
    }

    @Override
    public void modifyPopStatus(Long popId, Integer status) {
        SysPop sysPop = new SysPop();
        sysPop.setId(popId);
        sysPop.setStatus((byte) status.intValue());
        sysPopService.updateById(sysPop);
    }

    @Override
    public void modifyVisitorStatus(Long id, Integer status) {
        HttpServletRequest request = BaseUtil.getRequest();
        String remote = request.getRemoteHost();
        SysPop sysPop = sysPopService.getById(id);
        // 关闭通知
        if (status == 0) {
            // 拿到并设置失效时间
            Integer refreshTime = sysPop.getRefreshTime();
            stringRedisTemplate.opsForValue()
                    .set("SYSPOP:" + id + ":" + remote, "", refreshTime, TimeUnit.HOURS);
        }
    }

}
