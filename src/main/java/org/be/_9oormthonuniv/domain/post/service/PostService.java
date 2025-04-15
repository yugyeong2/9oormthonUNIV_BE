package org.be._9oormthonuniv.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.be._9oormthonuniv.domain.post.dto.PostRequest;
import org.be._9oormthonuniv.domain.post.dto.PostResponse;
import org.be._9oormthonuniv.domain.post.entity.Post;
import org.be._9oormthonuniv.domain.post.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final AWSS3Service awss3Service;

    public PostResponse createPost(PostRequest request) {
        // S3에 이미지 업로드하고, 반환받은 key 저장
        String key = awss3Service.uploadFile(request.getImage());

        // 게시글 엔티티 생성 및 저장
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .imageKey(key)
                .build();

        postRepository.save(post);

        // 응답 DTO 생성 및 반환
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .imageKey(key)
                .build();
    }

    public Long downloadPostImage(Long postId) {
        // 게시글 조회
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        String imageKey = post.getImageKey();
        return awss3Service.downloadFile(imageKey); // 이미지 key를 사용하여 S3에서 다운로드 수행
    }
}
