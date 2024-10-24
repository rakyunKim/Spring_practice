package kr.ccfc.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import kr.ccfc.base.BaseUtil;
import kr.ccfc.domain.CompetitionSchedule;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import kr.ccfc.mapper.RankingMapper;
import kr.ccfc.domain.Ranking;
import kr.ccfc.service.RankingService;
import org.springframework.util.CollectionUtils;

@Service
public class RankingServiceImpl extends ServiceImpl<RankingMapper, Ranking> implements RankingService {

    @Override
    public void exportExcel(List<String> ids) throws IOException {
        List<Ranking> list = list(new LambdaQueryWrapper<Ranking>()
                .in(
                        !CollectionUtils.isEmpty(ids), Ranking::getId, ids)
        );
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.addHeaderAlias("ranks", "리그");
        writer.addHeaderAlias("startTime", "리그 시작시간");
        writer.addHeaderAlias("endTime", "리그 끝시간");
        writer.addHeaderAlias("clubName", "구단명");
        writer.addHeaderAlias("season", "순위");
        writer.addHeaderAlias("currentCompetitionTimes", "경기수");
        writer.addHeaderAlias("points", "승점");
        writer.addHeaderAlias("win", "승");
        writer.addHeaderAlias("draw", "무");
        writer.addHeaderAlias("fail", "패");
        writer.addHeaderAlias("goalWin", "골득실");

        writer.addHeaderAlias("goalDraw", "득점");
        writer.addHeaderAlias("goalFail", "실점");
        writer.addHeaderAlias("totalCompetitionTimes", "경기수");
// 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
//out为OutputStream，需要写出到的目标流
        HttpServletResponse response = BaseUtil.getResponse();
//response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
//test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=ranking.xls");
        ServletOutputStream out = response.getOutputStream();

        writer.flush(out, true);
// 关闭writer，释放内存
        writer.close();
//此处记得关闭输出Servlet流
        IoUtil.close(out);
    }
}



