package org.be._9oormthonuniv.domain.post.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자의 접근 제어자를 protected로 설정
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    private String imageKey;

    @Builder
    public Post(String title, String content, String imageKey) {
        this.title = title;
        this.content = content;
        this.imageKey = imageKey;
    }
}