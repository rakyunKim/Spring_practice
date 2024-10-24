package kr.ccfc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import kr.ccfc.base.BaseUtil;
import kr.ccfc.domain.Comment;
import kr.ccfc.model.R;
import kr.ccfc.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@Slf4j
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping
    public R<String> comment(@RequestBody Comment comment) {
        comment.setCreateBy(BaseUtil.getUserId());
        commentService.save(comment);
        return R.ok();
    }

    @GetMapping("/getByArticle")
    public R<Page<Comment>> getArticleComments(Page<Comment> page, Long articleId) {
         Page<Comment> res = commentService.getArticleComments(page, articleId);
        return R.ok(res);
    }
}
