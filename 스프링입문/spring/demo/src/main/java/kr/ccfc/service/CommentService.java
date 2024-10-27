package kr.ccfc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import kr.ccfc.domain.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

public interface CommentService extends IService<Comment> {


    Page<Comment> getArticleComments(Page<Comment> page, Long articleId);
}





