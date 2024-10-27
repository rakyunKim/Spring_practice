package kr.ccfc.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import kr.ccfc.base.BaseUtil;
import kr.ccfc.domain.Article;
import kr.ccfc.domain.SysMenu;
import kr.ccfc.model.ArticleAttach;
import kr.ccfc.model.R;
import kr.ccfc.service.ArticleAttachService;
import kr.ccfc.service.ArticleService;
import kr.ccfc.service.SysMenuService;
import kr.ccfc.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.*;

@RestController
@RequestMapping("/portal/article")
@Slf4j
public class ArticleController {

    @Value("${upload.filepath}")
    private String filePath;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private ArticleAttachService articleAttachService;

    @Value("${server.port}")
    private String port;

    @PostMapping("/upload")
    public Map<String, Object> upload(MultipartFile file) throws UnknownHostException {

        Map<String, Object> res = new HashMap<>();
        res.put("errno", 0);
        List<Map<String, Object>> datas = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        try {
            String fileName = articleService.uploadImages(file);
            data.put("url", "/imgs/" + fileName);
            data.put("alt", "");
            data.put("href", "");
            datas.add(data);
            res.put("data", datas);
            return res;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            res.put("errno", 1);
            return res;
        }
    }

    @PostMapping
    public R<String> addArticle(@RequestBody @Valid Article article) {
        SysMenu menu = null;
        if (!StringUtils.isEmpty(article.getMenuUrl())) {
            String listUri = article.getMenuUrl().replaceAll("write", "list");
            menu = sysMenuService.getOne(new LambdaQueryWrapper<SysMenu>()
                    .eq(SysMenu::getTargetUrl, listUri)
                    .ne(SysMenu::getParentId, 0)
            );
        }
        boolean save = articleService.addArticle(article, menu);
        if (save) {
            return R.ok("저장 성공");
        } else {
            return R.fail("저장 실패");
        }
    }

    @PatchMapping
    public R<String> modifyArticle(@RequestBody @Valid Article article) {
        article.setStatus(1);
        article.setSort(0);
        article.setAuthorIp(BaseUtil.getRequest().getRemoteHost());
        boolean remove = articleAttachService.remove(
                new LambdaQueryWrapper<ArticleAttach>()
                        .eq(ArticleAttach::getArticleId, article.getId())
        );
        List<ArticleAttach> attaches = article.getAttaches();
        for (ArticleAttach attach : attaches) {
            attach.setArticleId(article.getId());
        }
        articleAttachService.saveBatch(attaches);
        boolean save = articleService.updateById(article);
        if (save) {
            return R.ok("저장 성공");
        } else {
            return R.fail("저장 실패");
        }
    }

    @GetMapping
    public R<Page<Article>> queryByPage(Page<Article> page, String title, Long userId,
                                        String author, String content, String titleOrContent, Integer type) {
        page.addOrder(OrderItem.desc("notice"));
        page.addOrder(OrderItem.desc("last_update_time"));
        Page<Article> articlePage = articleService.queryByPage(page, title,
                userId, author, content, titleOrContent, type);

        Date today = new Date();
        DateTime begin = DateUtil.beginOfDay(today);
        DateTime end = DateUtil.endOfDay(today);
        // 私密文章只有坐着本人能看到
        // 当天的文章添加 N 标志
        Iterator<Article> iterator = articlePage.getRecords().iterator();
        while (iterator.hasNext()) {
            Article article = iterator.next();
            Boolean secret = article.getSecret();
            if (BooleanUtil.isTrue(secret)) {
                Long curUserId = BaseUtil.getUserId();
                if (!ObjectUtil.equals(curUserId, article.getAuthorUserId())) {
                    // 非本人私密文章不允许查看
                    iterator.remove();
                }
            }
            Date created = article.getCreated();
            if (begin.before(created) && end.after(created)) {
                article.setNewArticle(true);
            }
            // 找到当前文章的所属菜单
            String articleType = article.getType();
            SysMenu menu = sysMenuService.getOne(new LambdaQueryWrapper<SysMenu>()
                    .eq(SysMenu::getArticleType, articleType)
            );
            String targetUrl = menu.getTargetUrl();
            if (!StringUtils.isEmpty(targetUrl)) {
                String[] split = targetUrl.split("/");
                if (split.length > 0) {
                    String target = split[split.length - 1];
                    if (target != null) {
                        target = target.replaceAll("_list", "_view");
                        targetUrl = targetUrl.replaceAll("_list", "_view");
                    }
                    article.setTarget(target);
                    article.setTargetUrl(targetUrl);
                }
            }
            article.setSysMenu(menu);

        }
        return R.ok(articlePage);
    }


    @GetMapping("/{id}")
    public R<Article> queryById(@PathVariable Long id) {
        Article article = articleService.getById(id);
        List<ArticleAttach> list = articleAttachService.list(
                new LambdaQueryWrapper<ArticleAttach>()
                        .eq(ArticleAttach::getArticleId, id)
        );
        article.setAttaches(list);
        // 阅读次数 ++
        Integer count = article.getCount();
        if (count == null) {
            count = 0;
        }
        article.setCount(++count);
        articleService.updateById(article);
        return R.ok(article);
    }

    @DeleteMapping
    public R<String> deleteById(Long[] ids) {
        for (Long id : ids) {
            boolean res = articleService.removeById(id);
            List<ArticleAttach> list = articleAttachService.list(new LambdaQueryWrapper<ArticleAttach>()
                    .eq(ArticleAttach::getArticleId, id)
            );
            for (ArticleAttach articleAttach : list) {
                String attachPath = articleAttach.getAttachPath();
                String name = FileNameUtil.getName(attachPath);
                FileUtil.del(filePath + "/" + name);
                articleAttachService.removeById(articleAttach.getId());
            }
        }
        return R.ok("삭제 성공");
    }

    @GetMapping("/copy")
    public R<String> copy(Long[] ids) {
        for (Long id : ids) {
            List<Article> list = articleService.list(
                    new LambdaQueryWrapper<Article>()
                            .eq(Article::getCopyId, id)
            );
            if (CollectionUtils.isEmpty(list)) {
                Article article = articleService.getById(id);
                article.setCopyId(article.getId());
                article.setType("100");
                article.setId(null);
                article.setStatus(0);
                articleService.save(article);
            }
        }
        return R.ok("백업 성공");
    }

    @PostMapping("/move")
    public R<String> move(@RequestBody JSONObject params) {
        Long menuId = params.getLong("menuId");
        String moveMode = params.getString("moveMode");
        SysMenu sysMenu = sysMenuService.getById(menuId);
        JSONArray articleIds = params.getJSONArray("articleIds");
        for (int i = 0; i < articleIds.size(); i++) {
            Long articleId = articleIds.getLong(i);
            Article article = articleService.getById(articleId);
            if ("copy".equals(moveMode)) {
                // 拷贝
                article.setType(sysMenu.getArticleType() + "");
                article.setId(null);
                articleService.save(article);
            } else {
                // 移动
                article.setType(sysMenu.getArticleType() + "");
                articleService.updateById(article);
            }
        }
        return R.ok("이동 성공");
    }

    @GetMapping("/attachDownload/{attachId}")
    public void downloadAttach(@PathVariable Long attachId) {
        ArticleAttach attach = articleAttachService.getById(attachId);
        attach.setDownloadCount(attach.getDownloadCount() == null ? 0 : attach.getDownloadCount() + 1);
        articleAttachService.updateById(attach);

        BufferedInputStream inputStream = null;
        try {
            ArticleAttach articleAttach = articleAttachService.getById(attachId);
            String attachPath = articleAttach.getAttachPath();
            String fileName = FileNameUtil.getName(attachPath);
            String file = filePath + "/" + fileName;
            HttpServletResponse response = BaseUtil.getResponse();
            Objects.requireNonNull(response).setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(attach.getName(), "UTF-8"));
            response.setContentType("application/octet-stream");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            inputStream = FileUtil.getInputStream(file);
            IoUtil.copy(inputStream, BaseUtil.getResponse().getOutputStream(), IoUtil.DEFAULT_BUFFER_SIZE);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            IoUtil.close(inputStream);
        }
    }

}
