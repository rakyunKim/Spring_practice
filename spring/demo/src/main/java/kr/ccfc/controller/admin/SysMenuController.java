package kr.ccfc.controller.admin;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import kr.ccfc.domain.Article;
import kr.ccfc.domain.SysMenu;
import kr.ccfc.model.MenuForm;
import kr.ccfc.model.R;
import kr.ccfc.service.ArticleService;
import kr.ccfc.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/menu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private ArticleService articleService;

    @GetMapping("/getById")
    public R<MenuForm> getById(Long id) {
        MenuForm menuForm = sysMenuService.getSysMenuAndPrivilegesById(id);
        return R.ok(menuForm);
    }

    @GetMapping("/getByUri")
    public R<SysMenu> getByUri(String uri) {
        SysMenu one = sysMenuService.getOne(new LambdaQueryWrapper<SysMenu>()
                .eq(SysMenu::getTargetUrl, uri)
                .ne(SysMenu::getParentId, 0)
        );
        return R.ok(one);
    }

    @GetMapping("/ck")
    public R<SysMenu> getWriteByUri(@RequestParam String uri) {
        String listUri = uri.replaceAll("write", "list");
        SysMenu one = sysMenuService.getOne(new LambdaQueryWrapper<SysMenu>()
                .eq(SysMenu::getTargetUrl, listUri)
                .ne(SysMenu::getParentId, 0)
        );
        return R.ok(one);

    }

    @GetMapping
    public Page<SysMenu> pageMenu(Page<SysMenu> menu) {
        return sysMenuService.page(menu);
    }

    @PatchMapping("/status")
    public R<String> channgeStatus(@RequestBody JSONObject param) {
        Long menuId = param.getLong("id");
        Byte status = param.getByte("status");
        sysMenuService.changeStatus(menuId, status);
        return R.ok();
    }

    /**
     * 分页获取菜单列表，不包括根节点
     */
    @GetMapping("/childmenus")
    public Page<SysMenu> pageChildMenus(Page<SysMenu> menu) {
        menu.addOrder(OrderItem.desc("last_update_time"));
        Page<SysMenu> list = sysMenuService.page(menu, new LambdaQueryWrapper<SysMenu>()
                .ne(SysMenu::getParentId, 0)
                .like(SysMenu::getTargetUrl, "_list")
        );
        List<SysMenu> records = list.getRecords();
        List<Long> parentIds = records.stream().map(SysMenu::getParentId).collect(Collectors.toList());
        List<SysMenu> parentMenus = sysMenuService.list(new LambdaQueryWrapper<SysMenu>()
                .in(SysMenu::getId, parentIds)
        );
        Map<Long, SysMenu> idMenuMap = parentMenus.stream().collect(Collectors.toMap(SysMenu::getId, e -> e));
        Map<Integer, Integer> articleCountMap = articleService.countByMenuType();
        for (SysMenu record : list.getRecords()) {
            record.setArticleCount(articleCountMap.get(record.getArticleType()) != null ? articleCountMap.get(record.getArticleType()) : 0);
            SysMenu parentMenu = idMenuMap.get(record.getParentId());
            record.setParentMenu(parentMenu);
            // 处理新
            Integer newArticle = articleService.count(new LambdaQueryWrapper<Article>()
                    .eq(Article::getType, record.getArticleType())
                    .between(Article::getCreated, DateUtil.beginOfDay(new Date()), DateUtil.endOfDay(new Date()))
            );
            record.setNewArticle(newArticle);
        }
        return list;
    }

    @GetMapping("/root")
    public R<List<SysMenu>> getRootMenus() {
        List<SysMenu> list = sysMenuService.list(new LambdaQueryWrapper<SysMenu>()
                .eq(SysMenu::getParentId, 0L)
        );
        return R.ok(list);
    }

    @PostMapping
    public R<String> insMenu(@RequestBody MenuForm menu) {
        // 创建菜单并添加菜单权限
        boolean save = sysMenuService.saveWithPrivileges(menu);
        if (save) {
            return R.ok("저장 성공");
        } else {
            return R.fail("저장 실패");
        }
    }

    /**
     * 获取所有不需要登录就能访问的菜单 以及菜单权限
     * 即 10 级角色权限
     */
    @GetMapping("/nologinMenus")
    public R<List<SysMenu>> getNoLoginMenus() {
        List<SysMenu> menus = sysMenuService.getNoLoginMenus();
        return R.ok(menus);
    }

    /**
     * 拿到权限内的菜单树
     */
    @GetMapping("/menutrees")
    public R<List<SysMenu>> menuTrees() {
        List<SysMenu> sysMenus = sysMenuService.menuTrees();
        return R.ok(sysMenus);
    }
}
