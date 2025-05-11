package org.be._9oormthonuniv.domain.post.repository;

import org.be._9oormthonuniv.domain.post.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
