package kr.ccfc.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import kr.ccfc.base.BaseUtil;
import kr.ccfc.domain.SysPop;
import kr.ccfc.model.R;
import kr.ccfc.service.SysPopIpService;
import kr.ccfc.service.SysPopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 弹窗控制器
 */
@RestController
@Slf4j
@RequestMapping("/syspop")
public class SysPopController {
    @Autowired
    private SysPopService sysPopService;

    @Autowired
    private SysPopIpService sysPopIpService;

    @PostMapping
    public R<String> saveSysPop(@RequestBody SysPop sysPop) {
        if (sysPop.getId() == null) {
            sysPopService.save(sysPop);
        } else {
            sysPopService.updateById(sysPop);
        }
        return R.ok();
    }

    @GetMapping("/list")
    public R<Page<SysPop>> getList(Page<SysPop> sysPops) {
        sysPops.addOrder(OrderItem.desc("last_update_time"));
        Page<SysPop> page = sysPopService.page(sysPops);
        return R.ok(page);
    }

    @GetMapping("/getById")
    public R<SysPop> getSysPopById(Long id) {
        SysPop sysPop = sysPopService.getById(id);
        return R.ok(sysPop);
    }

    /**
     * 获取 当前访问者的所有需要展示的弹窗
     * 根据ip 记录是否展示
     */
    @GetMapping("/showPop")
    public R<List<SysPop>> getPopList() {
        String remote = BaseUtil.getRequest().getRemoteHost();
        log.info(">>>>>>>>>>>>>>> visitor ip:{}", remote);
        List<SysPop> list = sysPopIpService.getPopList(remote);
        return R.ok(list);
    }

    /**
     * 修改弹窗展是否展示
     */
    @PatchMapping("/status")
    public R<String> modify(@RequestBody JSONObject param) {
        Long id = param.getLong("id");
        Integer status = param.getInteger("status");
        sysPopIpService.modifyPopStatus(id, status);
        return R.ok("수정 성공");
    }

    @PatchMapping("/visitorstatus")
    public R<SysPop> visitorstatus(@RequestBody JSONObject param) {
        Long id = param.getLong("id");
        Integer status = param.getInteger("status");
        sysPopIpService.modifyVisitorStatus(id, status);
        return R.ok();
    }

    @DeleteMapping
    public R<String> deleteById(Long[] ids) {
        for (Long id : ids) {
            boolean res = sysPopService.removeById(id);
        }
        return R.ok("삭제 성공");
    }
}
