package kr.ccfc.controller;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import kr.ccfc.domain.CompetitionSchedule;
import kr.ccfc.model.R;
import kr.ccfc.service.CompetitionScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/competition")
@Slf4j
public class CompetitionScheduleController {

    @Autowired
    private CompetitionScheduleService competitionScheduleService;

    @PostMapping
    public R<String> save(@RequestBody CompetitionSchedule competitionSchedule) {
    	if(competitionSchedule.getDateTime() == null) {
    		return R.fail();
    	}
        if (competitionSchedule.getId() != null) {
            competitionScheduleService.updateById(competitionSchedule);
        } else {
            competitionScheduleService.save(competitionSchedule);
        }
        return R.ok();
    }

    @GetMapping("/list")
    public R<Page<CompetitionSchedule>> list(Page<CompetitionSchedule> page, @DateTimeFormat(pattern = "yyyy-MM-dd")
            Date start, @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                     Date end) {
        if (end != null) {
            DateTime endTime = DateTime.of(end);
            DateTime offset = endTime.offset(DateField.DAY_OF_MONTH, 1);
            end = offset.toJdkDate();
        }
        Page<CompetitionSchedule> res = competitionScheduleService.page(page,
                new LambdaQueryWrapper<CompetitionSchedule>()
                        .between(start != null && end != null, CompetitionSchedule::getDateTime, start, end)
                        .orderByAsc(CompetitionSchedule::getDateTime)
        );
        List<CompetitionSchedule> records = res.getRecords();
        String today = DateUtil.today();
        boolean flag = true;
        for (CompetitionSchedule record : records) {
            String time = DateUtil.format(record.getDateTime(), "HH:mm");
            String day = DateUtil.format(record.getDateTime(), "yyyy-MM-dd");
            String week = DateUtil.format(record.getDateTime(), "E");
            record.setDay(day);
            record.setTime(time);
            record.setWeek(week);
            if (flag) {
                if (today.equals(day)) {
                    record.setShow(true);
                    flag = false;
                }
            }
        }
        if (flag) {
            CompetitionSchedule date = findDate(records, new Date());
            if (date != null) {
                date.setShow(true);
            }
        }

        return R.ok(res);
    }

    /**
     * 找到距离今天最近的一天
     */
    private CompetitionSchedule findDate(List<CompetitionSchedule> list, Date d) {
        if (list == null || list.size() <= 0) {
            return null;
        }
        long gap = Long.MAX_VALUE;
        CompetitionSchedule r = null;
        long time = d.getTime();
        for (CompetitionSchedule competitionSchedule : list) {
            Date t = competitionSchedule.getDateTime();
            long tm = Math.abs(time - t.getTime());
            if (gap > tm) {
                gap = tm;
                r = competitionSchedule;
            }
        }
        return r;
    }

    @GetMapping("/calendar")
    public R<Map<String, List<CompetitionSchedule>>> calendar(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        Date start = DateUtil.beginOfMonth(date).toJdkDate();
        Date end = DateUtil.endOfMonth(date).toJdkDate();

        List<CompetitionSchedule> records = competitionScheduleService.list(
                new LambdaQueryWrapper<CompetitionSchedule>()
                        .between(CompetitionSchedule::getDateTime, start, end)
                        .orderByDesc(CompetitionSchedule::getLastUpdateTime)
        );
        for (CompetitionSchedule record : records) {
            String time = DateUtil.formatTime(record.getDateTime());
            String day = DateUtil.format(record.getDateTime(), "yyyy-MM-dd");
            record.setDay(day);
            record.setTime(time);
        }

        Map<String, List<CompetitionSchedule>> results = records.stream()
                .collect(Collectors.groupingBy(e -> DateUtil.format(e.getDateTime(), "yyyy-MM-dd")));
        return R.ok(results);
    }


    @DeleteMapping
    public R<String> deleteById(Long[] ids) {
        for (Long id : ids) {
            boolean res = competitionScheduleService.removeById(id);
        }
        return R.ok("삭제 성공");
    }


    @PostMapping("/downloadExcel")
    public R<String> downloadExcel(@RequestBody List<String> ids) {
        try {
            competitionScheduleService.exportExcel(ids);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return R.ok();
    }


}
