package kr.ccfc.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jodd.time.TimeUtil;
import kr.ccfc.domain.SysUser;
import kr.ccfc.domain.SysUserStatis;
import kr.ccfc.mapper.SysUserStatisMapper;
import kr.ccfc.service.MailService;
import kr.ccfc.service.SysUserStatisService;
import kr.ccfc.vo.ChartsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class SysUserStatisServiceImpl extends ServiceImpl<SysUserStatisMapper, SysUserStatis> implements SysUserStatisService {

    @Autowired
    private SysUserStatisMapper sysUserStatisMapper;

    @Override
    public ChartsVo statisToCharts(Date start, Date end) {
        List<Map<String, Object>> rows = new ArrayList<>();
        Date startDate;
        Date endDate;
        if (start == null || end == null) {
            // 查询最近7天内的访问记录
            DateTime now = DateTime.of(new Date());
            now.setMutable(false);
            DateTime offset = now.offset(DateField.DAY_OF_MONTH, -30);
            startDate = offset.toJdkDate();
            endDate = now.toJdkDate();
        } else {
            startDate = start;
            DateTime endTime = DateTime.of(end);
            DateTime offset = endTime.offset(DateField.DAY_OF_MONTH, 1);
            endDate = offset.toJdkDate();
        }
        List<SysUserStatis> sysUserStatis = sysUserStatisMapper.statisToCharts(startDate, endDate);
        for (SysUserStatis statis : sysUserStatis) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("날짜", DateTime.of(statis.getLastUpdateTime()).toString("MM/dd"));
            row.put("방문수", statis.getCount());
            rows.add(row);
        }
        ChartsVo chartsVo = new ChartsVo();
        chartsVo.setRows(rows);
        List<String> columns = new ArrayList<>();
        columns.add("날짜");
        columns.add("방문수");
        chartsVo.setColumns(columns);
        return chartsVo;
    }

}




