package org.be._9oormthonuniv.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private String imageKey;
}
