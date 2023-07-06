package com.example.knupractice.web.controller;

import com.example.knupractice.domain.blog.Article;
import com.example.knupractice.service.BlogService;
import com.example.knupractice.web.dto.ArticleListViewResponseDto;
import com.example.knupractice.web.dto.ArticleResponseDto;
import com.example.knupractice.web.dto.ArticleViewResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BlogViewController {

    private final BlogService blogService;

    // 게시글 조회
    @GetMapping("/articles")
    public String getArticles(Model model) {
        List<ArticleListViewResponseDto> articles = blogService.findAll()
                .stream()
                .map(ArticleListViewResponseDto::new)
                .collect(Collectors.toList());

        model.addAttribute("articles", articles);
        return "articleList";
    }

    // 게시글 단건 조회
    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        Article article = blogService.findById(id);
        model.addAttribute("article", new ArticleListViewResponseDto(article));

        return "article";
    }

    // 신규 글 등록과 수정을 동시에 처리
    // required = false -> 아이디 값이 null이어도 괜찮다.
    // 아이디가 null 이면 신규 등록, null이 아니라면 수정 작업을 해준다.
    @GetMapping("/new-article")
    public String newArticle(@RequestParam(required = false) Long id, Model model) {
        if(id == null) {
            model.addAttribute("article", new ArticleViewResponseDto());
        } else {
            Article article = blogService.findById(id); // 수정할 아티클
            model.addAttribute("article", new ArticleViewResponseDto(article));
        }

        return "newArticle";
    }


}
