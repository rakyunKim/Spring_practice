package kr.ccfc.service;

import kr.ccfc.domain.MemberApply;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;
import java.util.List;

public interface MemberApplyService extends IService<MemberApply> {


    void exportExcel(List<String> ids) throws IOException;
}



