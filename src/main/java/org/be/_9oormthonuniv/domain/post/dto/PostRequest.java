package org.be._9oormthonuniv.domain.post.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
@Builder
public class PostRequest {
    private String title;
    private String content;
    private MultipartFile image;

    @Builder
    public PostRequest(String title, String content, MultipartFile image) {
        this.title = title;
        this.content = content;
        this.image = image;
    }
}
