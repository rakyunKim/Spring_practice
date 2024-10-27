package kr.ccfc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import kr.ccfc.domain.Article;
import kr.ccfc.domain.SysMenu;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface ArticleService extends IService<Article> {

    String uploadImages(MultipartFile file) throws IOException;

    Page<Article> queryByPage(Page<Article> page, String title, Long userId,
                              String author, String content, String titleOrContent, Integer type);


    Map<Integer, Integer> countByMenuType();

    boolean addArticle(Article article, SysMenu menu);
}
