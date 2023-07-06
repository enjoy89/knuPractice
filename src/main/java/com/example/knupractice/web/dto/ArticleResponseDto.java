package com.example.knupractice.web.dto;

import com.example.knupractice.domain.blog.Article;
import lombok.Getter;

// request 받아온 entity를 dto로 바꿔야됨
@Getter
public class ArticleResponseDto {
    private String title;
    private String content;

    public ArticleResponseDto(Article article) {
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
