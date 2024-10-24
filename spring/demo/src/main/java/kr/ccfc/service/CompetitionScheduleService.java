package kr.ccfc.service;

import com.alibaba.fastjson.JSONArray;
import kr.ccfc.domain.CompetitionSchedule;
import com.baomidou.mybatisplus.extension.service.IService;
import org.dom4j.DocumentException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface CompetitionScheduleService extends IService<CompetitionSchedule> {


    void generateExcel(String dom, HttpServletResponse response) throws DocumentException, IOException;

    void exportExcel(List<String> ids) throws IOException;
}




