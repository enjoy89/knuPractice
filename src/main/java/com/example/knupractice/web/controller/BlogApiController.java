package com.example.knupractice.web.controller;

import com.example.knupractice.domain.blog.Article;
import com.example.knupractice.service.BlogService;
import com.example.knupractice.web.dto.AddArticleRequestDto;
import com.example.knupractice.web.dto.ArticleResponseDto;
import com.example.knupractice.web.dto.UpdateArticleRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BlogApiController {

    private final BlogService blogService;

    @PostMapping("/api/latest/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(blogService.save(requestDto));
    }

    @GetMapping("/api/latest/articles")
    public ResponseEntity<List<ArticleResponseDto>> findAllArticeles() {

        // 아티클 디티오를 엔티티로 바꿈
        List<ArticleResponseDto> articles = blogService.findAll()
                .stream().map(ArticleResponseDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(articles);
    }

    @GetMapping("/api/latest/articles/{id}")
    public ResponseEntity<ArticleResponseDto> findArticle(@PathVariable Long id) {
        return ResponseEntity.ok()
                .body(new ArticleResponseDto(blogService.findById(id)));
    }

    @DeleteMapping("/api/latest/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        blogService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/latest/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody UpdateArticleRequestDto requestDto) {

        Article updatedArticle = blogService.update(id, requestDto);
        return ResponseEntity.ok().body(updatedArticle);

    }
}
