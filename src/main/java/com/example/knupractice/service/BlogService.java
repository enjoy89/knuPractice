package com.example.knupractice.service;

import com.example.knupractice.domain.blog.Article;
import com.example.knupractice.repository.BlogRepository;
import com.example.knupractice.web.dto.AddArticleRequestDto;
import com.example.knupractice.web.dto.UpdateArticleRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;

    // save
    public Article save(AddArticleRequestDto requestDto) {
        return blogRepository.save(requestDto.toEntity());
    }
    // tranctional - readOnly => 객체를 읽는 과정이므로 속도처리를 빠르게 하기 위해서 읽기 기능만 하기
    // all read
    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    // read only 1
    public Article findById(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("article not exist!: " + id));
    }

    public void delete(Long id) {
        blogRepository.deleteById(id);
    }

    // 기존 게시글을 수정
    //
    @Transactional
    public Article update(Long id, UpdateArticleRequestDto requestDto) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("article not exist!: " + id));

        // 실제 객체가 업데이트 되는 것은 엔티티에서 정의해야 한다.
        // 그럼 왜 서비스로직에서 이 부분을 구현해?
        // 작업의 순서를 정의해주기 위해서 ?


        // 영속성 컨텍스트의 개념에 의해서 -> 자동으로 업데이트 동작이 반영된다.
        article.update(requestDto.getTitle(), requestDto.getContnet());
        return article;
    }

}
