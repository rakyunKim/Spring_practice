package kr.ccfc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import kr.ccfc.domain.Article;
import kr.ccfc.model.ArticleVo;

import java.util.List;
import java.util.Map;

public interface ArticleMapper extends BaseMapper<Article> {

    List<Article> queryArticleList(Page<Article> page, String title,
                                   Long userId, Long authorId, String content);

    List<ArticleVo> countByMenuType();
}
