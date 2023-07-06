package com.example.knupractice.repository;

import com.example.knupractice.domain.blog.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {
}
