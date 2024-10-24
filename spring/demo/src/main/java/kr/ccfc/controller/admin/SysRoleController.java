package kr.ccfc.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import kr.ccfc.domain.SysRole;
import kr.ccfc.model.R;
import kr.ccfc.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 获取系统所有角色(超管除外)
     */
    @GetMapping
    public R<List<SysRole>> getRoles() {
        List<SysRole> list = sysRoleService.list(new LambdaQueryWrapper<SysRole>()
                .ne(SysRole::getCode, "ROLE_ADMIN")
        );
        return R.ok(list);
    }

}
