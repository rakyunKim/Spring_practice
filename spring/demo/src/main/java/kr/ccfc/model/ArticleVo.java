package kr.ccfc.model;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleVo {

    private Integer type;

    private Integer count;

    private Date created;
}
