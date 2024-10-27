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
import kr.ccfc.mapper.MemberApplyMapper;
import kr.ccfc.domain.MemberApply;
import kr.ccfc.service.MemberApplyService;
import org.springframework.util.CollectionUtils;

@Service
public class MemberApplyServiceImpl extends ServiceImpl<MemberApplyMapper, MemberApply> implements MemberApplyService {

    @Override
    public void exportExcel(List<String> ids) throws IOException {
        List<MemberApply> list = list(new LambdaQueryWrapper<MemberApply>()
                .in(
                        !CollectionUtils.isEmpty(ids), MemberApply::getId, ids)
        );
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.addHeaderAlias("lastUpdateTime", "신청시간");
        writer.addHeaderAlias("userName", "접속아이디");
        writer.addHeaderAlias("companyName", "이름(법인)");
        writer.addHeaderAlias("registerNo", "주민번호");
        writer.addHeaderAlias("email", "이메일");
        writer.addHeaderAlias("address", "주소");
        writer.addHeaderAlias("registerType", "조합");
        writer.addHeaderAlias("checkBusiness", "개인");
        writer.addHeaderAlias("service", "기업");
        writer.addHeaderAlias("cooperator", "구좌수");
        writer.addHeaderAlias("cooperatorMoney", "구좌 총금액");
        writer.addHeaderAlias("payMoney", "일시납부");
        writer.addHeaderAlias("monthPay", "월납부신청");
        writer.addHeaderAlias("payDate", "매달출금일");
        writer.addHeaderAlias("monthPay", "금액");
        writer.addHeaderAlias("monthPay", "정기금액");
        writer.addHeaderAlias("payType", "일시납부금액");
        writer.addHeaderAlias("holderName", "예금주");
        writer.addHeaderAlias("bankName", "은행명");
        writer.addHeaderAlias("holderBirth", "예금주 생년월일");
        writer.addHeaderAlias("account", "계좌번호");

        //writer.merge(1, 1, 1, 5, "후원형태", false);
//        writer.merge(1, 5, 3, 6, 2, 4, "신청일시", "기본정보", "가입형태", "후원형태", "기본정보", "은행정보");
//        writer.merge(5, "기본정보SS");
//        writer.merge(3, "가입형태");
//        writer.merge(6, "후원형태");
//        writer.merge(2, "기본정보");
//        writer.merge(4, "은행정보");

// 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
//out为OutputStream，需要写出到的目标流
        HttpServletResponse response = BaseUtil.getResponse();
//response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
//test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=memberapply.xls");
        ServletOutputStream out = response.getOutputStream();

        writer.flush(out, true);
// 关闭writer，释放内存
        writer.close();
//此处记得关闭输出Servlet流
        IoUtil.close(out);
    }
}



