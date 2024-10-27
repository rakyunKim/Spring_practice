package kr.ccfc.service;

import kr.ccfc.domain.Ranking;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;
import java.util.List;

public interface RankingService extends IService<Ranking> {


    void exportExcel(List<String> ids) throws IOException;
}



