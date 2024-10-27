package kr.ccfc.controller;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import kr.ccfc.domain.Ranking;
import kr.ccfc.model.R;
import kr.ccfc.service.RankingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/ranking")
@Slf4j
public class RankingController {

    @Autowired
    private RankingService rankingService;

    @PostMapping
    public R<String> save(@RequestBody Ranking ranking) {
        if (ranking.getId() != null) {
            rankingService.updateById(ranking);
        } else {
            rankingService.save(ranking);
        }
        return R.ok();
    }

    @GetMapping("/list")
    public R<Page<Ranking>> list(Page<Ranking> page, @DateTimeFormat(pattern = "yyyy-MM-dd")
            Date start, @DateTimeFormat(pattern = "yyyy-MM-dd")
                                         Date end) {
        if (end != null) {
            DateTime endTime = DateTime.of(end);
            DateTime offset = endTime.offset(DateField.DAY_OF_MONTH, 1);
            end = offset.toJdkDate();
        }
        Page<Ranking> res = rankingService.page(page,
                new LambdaQueryWrapper<Ranking>()
                        .lt(start != null, Ranking::getStartTime, start)
                        .gt(end != null, Ranking::getEndTime, end)
                        .orderByAsc(Ranking::getRanks)
        );
        return R.ok(res);
    }


    @DeleteMapping
    public R<String> deleteById(Long[] ids) {
        for (Long id : ids) {
            boolean res = rankingService.removeById(id);
        }
        return R.ok("삭제 성공");
    }

    @PostMapping("/downloadExcel")
    public R<String> downloadExcel(@RequestBody List<String> ids) {
        try {
            rankingService.exportExcel(ids);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return R.ok();
    }


}
