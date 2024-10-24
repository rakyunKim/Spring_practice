package kr.ccfc.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import kr.ccfc.mapper.CommentMapper;
import kr.ccfc.domain.Comment;
import kr.ccfc.service.CommentService;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Page<Comment> getArticleComments(Page<Comment> page, Long articleId) {
        return commentMapper.getArticleComments(page, articleId);
    }
}





