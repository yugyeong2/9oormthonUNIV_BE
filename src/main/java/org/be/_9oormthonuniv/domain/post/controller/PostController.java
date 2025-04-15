package org.be._9oormthonuniv.domain.post.controller;

import org.be._9oormthonuniv.domain.post.dto.PostRequest;
import org.be._9oormthonuniv.domain.post.dto.PostResponse;
import org.be._9oormthonuniv.domain.post.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/upload")
    public ResponseEntity<PostResponse> uploadImage(
            // MultipartFile이 포함된 경우에는 반드시 @RequestPart를 써야 서버가 요청을 제대로 파싱
            @RequestPart String title,
            @RequestPart String content,
            @RequestPart MultipartFile image
    ) {
        // 요청 데이터를 DTO로 변환
        PostRequest request = PostRequest.builder()
                .title(title)
                .content(content)
                .image(image)
                .build();

        PostResponse response = postService.createPost(request); // 게시글 생성
        return ResponseEntity.status(HttpStatus.CREATED).body(response); // 201 CREATED
    }

    @PostMapping("/download")
    public ResponseEntity<String> downloadImage(@RequestParam Long postId) {
        Long size = postService.downloadPostImage(postId); // 이미지 다운로드
        return ResponseEntity.status(HttpStatus.OK).body("게시글 이미지 다운로드 완료 (크기: " + size + " bytes)"); // 200 OK
    }
}