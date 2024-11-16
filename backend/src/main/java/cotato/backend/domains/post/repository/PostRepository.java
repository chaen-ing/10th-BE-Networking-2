package cotato.backend.domains.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cotato.backend.domains.post.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
