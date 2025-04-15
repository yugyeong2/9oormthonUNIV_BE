package org.be._9oormthonuniv.domain.post.repository;

import org.be._9oormthonuniv.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
