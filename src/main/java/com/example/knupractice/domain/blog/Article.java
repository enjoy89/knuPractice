package com.example.knupractice.domain.blog;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 보안 관점에서 유리
@EntityListeners(AuditingEntityListener.class)
@Getter
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // id, createAt, updateAt 은 자동으로 관리되므로 추가 X
    @Builder
    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // update 하는 기능을 명시한다.
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
