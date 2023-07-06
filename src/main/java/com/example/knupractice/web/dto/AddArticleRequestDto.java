package com.example.knupractice.web.dto;

import com.example.knupractice.domain.blog.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddArticleRequestDto {
    private String title;
    private String content;


    // 엔티티는 오직 데이터베이스에 넣을 때만 바뀜
    // Dto를 통해 데이터를 변경시킨다.

    public Article toEntity() {
        return Article.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }



}
