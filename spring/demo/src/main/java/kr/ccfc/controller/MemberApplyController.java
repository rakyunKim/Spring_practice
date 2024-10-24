package kr.ccfc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import kr.ccfc.base.BaseUtil;
import kr.ccfc.domain.MemberApply;
import kr.ccfc.domain.SysUser;
import kr.ccfc.model.R;
import kr.ccfc.service.MemberApplyService;
import kr.ccfc.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/memberapply")
@Slf4j
public class MemberApplyController {

    @Autowired
    private MemberApplyService memberApplyService;
    @Autowired
    private SysUserService sysUserService;

    @PostMapping
    public R<String> save(@RequestBody MemberApply memberApply) {
        Long applyUserId = memberApply.getUserId();
        if(applyUserId==null){
            // 前台新增
            Long userId = BaseUtil.getUserId();
            SysUser sysUser = sysUserService.getById(userId);
            sysUser.setMemberApply((byte) 1);
            memberApply.setUserName(sysUser.getUsername());
            memberApply.setUserId(userId);
            if (memberApply.getId() == null) {
                memberApplyService.save(memberApply);
            } else {
                memberApplyService.updateById(memberApply);
            }
            sysUserService.updateById(sysUser);
        }else{
            // 後臺編輯
            memberApplyService.updateById(memberApply);
        }
        return R.ok("저장 성공");
    }

    @GetMapping("/page")
    public R<Page<MemberApply>> page(Page<MemberApply> page, String username, String nickName,
                                     String realName, String mobile, @DateTimeFormat(pattern = "yyyy-MM-dd") Date created) {
        List<SysUser> list = sysUserService.list(
                new LambdaQueryWrapper<SysUser>()
                        .like(!StringUtils.isEmpty(nickName), SysUser::getNickName, nickName)
                        .like(!StringUtils.isEmpty(realName), SysUser::getRealName, realName)
        );
        List<Long> ids = list.stream().map(SysUser::getId)
                .collect(Collectors.toList());
        page.addOrder(OrderItem.desc("last_update_time"));
        Page<MemberApply> res = memberApplyService.page(page, new LambdaQueryWrapper<MemberApply>()
                .like(!StringUtils.isEmpty(username), MemberApply::getUserName, username)
                .eq(!StringUtils.isEmpty(mobile), MemberApply::getMobile, mobile)
                .between(created != null, MemberApply::getCreated, created, created + "23:59:59")
                .in(!CollectionUtils.isEmpty(ids), MemberApply::getUserId, ids)
        );
        return R.ok(res);
    }

    @DeleteMapping
    public R<String> deleteById(Long[] ids) {
        for (Long id : ids) {
            memberApplyService.removeById(id);
        }
        return R.ok();
    }

    @GetMapping("/getById")
    public R<MemberApply> getById(Long id) {
        MemberApply memberApply = memberApplyService.getById(id);
        return R.ok(memberApply);
    }


    @PostMapping("/downloadExcel")
    public R<String> downloadExcel(@RequestBody List<String> ids) {
        try {
            memberApplyService.exportExcel(ids);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return R.ok();
    }
}
