package org.be._9oormthonuniv.domain.post.controller;

import lombok.RequiredArgsConstructor;
import org.be._9oormthonuniv.domain.post.dto.CommentRequest;
import org.be._9oormthonuniv.domain.post.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/{postId}/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<String> createComment(@PathVariable Long postId, @RequestBody CommentRequest request) {
        commentService.createComment(postId, request); // DB 저장
        return ResponseEntity.ok("댓글 등록 완료 - 비동기 알림 발송");
    }
}
