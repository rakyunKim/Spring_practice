package kr.ccfc.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import kr.ccfc.base.BaseUtil;
import kr.ccfc.domain.CompetitionSchedule;
import kr.ccfc.mapper.CompetitionScheduleMapper;
import kr.ccfc.service.CompetitionScheduleService;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class CompetitionScheduleServiceImpl extends ServiceImpl<CompetitionScheduleMapper, CompetitionSchedule> implements CompetitionScheduleService {

    @Autowired
    private CompetitionScheduleService competitionScheduleService;

    @Override
    public void generateExcel(String dom, HttpServletResponse response) throws DocumentException, IOException {
//        dom = dom.replaceAll("<colgroup>(.*?)</colgroup>", "")
//                .replaceAll("<[img|IMG].*? src=[\\'|\\\"](.*?)[\\'|\\\"].*?[\\/]?>", "")
//                .replaceAll("th", "td")
//                .replaceAll("<br>", "")
//        ;
//        HSSFWorkbook sheets = ConvertHtml2Excel.table2Excel(dom);
//        response.setHeader("Content-Disposition", "attachment; fileName=excel.xlsx");
//        sheets.write(response.getOutputStream());
    }

    @Override
    public void exportExcel(List<String> ids) throws IOException {
        List<CompetitionSchedule> list = competitionScheduleService.list(new LambdaQueryWrapper<CompetitionSchedule>()
                .in(
                        !CollectionUtils.isEmpty(ids), CompetitionSchedule::getId, ids)
        );
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.addHeaderAlias("dateTime", "경기시간");
        writer.addHeaderAlias("place", "장소");
        writer.addHeaderAlias("season", "리그");
        writer.addHeaderAlias("homeTeam", "홈팀");
        writer.addHeaderAlias("visitTeam", "어웨이팀");
        writer.addHeaderAlias("homeScope", "홈팀 점수");
        writer.addHeaderAlias("visitScope", "어웨이팀 점수");
        writer.addHeaderAlias("lastUpdateTime", "수정시간");

// 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
//out为OutputStream，需要写出到的目标流
        HttpServletResponse response = BaseUtil.getResponse();
//response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
//test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=gamelist.xls");
        ServletOutputStream out = response.getOutputStream();

        writer.flush(out, true);
// 关闭writer，释放内存
        writer.close();
//此处记得关闭输出Servlet流
        IoUtil.close(out);
    }

}




