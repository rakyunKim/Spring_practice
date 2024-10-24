package kr.ccfc.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import kr.ccfc.base.BaseUtil;
import kr.ccfc.domain.Article;
import kr.ccfc.domain.SysMenu;
import kr.ccfc.domain.SysUser;
import kr.ccfc.mapper.ArticleMapper;
import kr.ccfc.model.ArticleAttach;
import kr.ccfc.model.ArticleVo;
import kr.ccfc.service.ArticleAttachService;
import kr.ccfc.service.ArticleService;
import kr.ccfc.service.SysMenuService;
import kr.ccfc.service.SysUserService;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Value("${upload.filepath}")
    private String filePath;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ArticleAttachService articleAttachService;

    @Autowired
    private SysMenuService sysMenuService;


    @Override
    public String uploadImages(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        //保存文件
        String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));  // 后缀名
        String fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath + "/" + fileName);
        if (!dest.getParentFile().exists()) {
            boolean mkdirs = dest.getParentFile().mkdirs();
            if (!mkdirs) {
                throw new IOException("파일 업로드 실패");
            }
        }
        file.transferTo(dest);
        return fileName;
    }

    @Override
    public Page<Article> queryByPage(Page<Article> page, String title,
                                     Long userId, String author, String content, String titleOrContent, Integer type) {
        return page(page, new LambdaQueryWrapper<Article>()
                .eq(type != null, Article::getType, type)
                .like(!StringUtils.isEmpty(title), Article::getTitle, title)
                .eq(Article::getSecret, (byte) 0)
                .like(!StringUtils.isEmpty(author), Article::getAuthor, author)
                .like(!StringUtils.isEmpty(content), Article::getContent, content)
                .and(!StringUtils.isEmpty(titleOrContent), wr -> wr.like(!StringUtils.isEmpty(titleOrContent), Article::getTitle, titleOrContent)
                        .or()
                        .like(!StringUtils.isEmpty(titleOrContent), Article::getContent, titleOrContent)
                )
        );
    }

    @Override
    public Map<Integer, Integer> countByMenuType() {
        List<ArticleVo> articleVos = articleMapper.countByMenuType();
        return articleVos.stream().collect(Collectors.toMap(ArticleVo::getType, ArticleVo::getCount));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addArticle(Article article, SysMenu menu) {
        Long userId = BaseUtil.getUserId();
        SysUser user = sysUserService.getById(userId);
        article.setAuthorUserId(userId);
        article.setAuthor(user.getRealName());
        article.setStatus(1);
        article.setSort(0);
        save(article);
        if (menu != null) {
            Byte useSecret = menu.getUseSecret();
            if (useSecret == (byte) 0) {
                // 不使用私密
                article.setSecret(false);
            } else if (useSecret == (byte) 1) {
                article.setSecret(true);
            }
            Boolean useRealName = menu.getUseRealName();
            if (BooleanUtils.isTrue(useRealName)) {
                // 使用真名 or 使用昵称
                article.setAuthor(user.getRealName());
            } else {
                article.setAuthor(user.getNickName());
            }
            Boolean ipVisible = menu.getIpVisible();
            if (BooleanUtils.isTrue(ipVisible)) {
                String remoteHost = BaseUtil.getRequest().getRemoteHost();
                article.setAuthorIp(remoteHost);
            } else {
                article.setAuthorIp("unknown");
            }
            List<ArticleAttach> attaches = article.getAttaches();
            Integer fileLimit = menu.getFileLimit() == null ? 0 : menu.getFileLimit();
            if (attaches.size() > fileLimit) {
                throw new IllegalArgumentException("최대" + fileLimit + "개 파일 업로드가능합니다");
            }
            for (ArticleAttach attach : attaches) {
                attach.setArticleId(article.getId());
                String attachPath = attach.getAttachPath();
                String fileName = FileNameUtil.getName(attachPath);
                String file = filePath + "/" + fileName;
                long size = FileUtil.size(new File(file));
                attach.setSize((int) size);
            }
            articleAttachService.saveBatch(attaches);
        }
        return true;
    }

}

