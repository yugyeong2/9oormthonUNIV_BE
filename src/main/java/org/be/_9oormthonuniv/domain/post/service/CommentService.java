package org.be._9oormthonuniv.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.be._9oormthonuniv.domain.post.dto.CommentRequest;
import org.be._9oormthonuniv.domain.post.entity.Comment;
import org.be._9oormthonuniv.domain.post.entity.Post;
import org.be._9oormthonuniv.domain.post.repository.CommentRepository;
import org.be._9oormthonuniv.domain.post.repository.PostRepository;
import org.be._9oormthonuniv.global.kafka.KafkaProducerService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final KafkaProducerService kafkaProducerService;

    public void createComment(Long postId, CommentRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        Comment comment = Comment.builder()
                .writer(request.getWriter())
                .content(request.getContent())
                .post(post)
                .build();

        commentRepository.save(comment);

        String message = "댓글 등록: 게시글ID=" + postId + ", 작성자=" + request.getWriter();
        kafkaProducerService.sendMessage("comments", message);
    }
}
