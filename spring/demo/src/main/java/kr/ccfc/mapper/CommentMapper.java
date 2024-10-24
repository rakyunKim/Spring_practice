package kr.ccfc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import kr.ccfc.domain.Comment;

public interface CommentMapper extends BaseMapper<Comment> {
    Page<Comment> getArticleComments(Page<Comment> page, Long articleId);
}
