package cotato.backend.domains.post.dto.response;

import cotato.backend.domains.post.domain.Post;
import lombok.Builder;

@Builder
public record PostResponse(
	Long id,
	String title,
	String content,
	String name,
	Long views
) {
	public static PostResponse from(Post post) {
		return PostResponse.builder()
			.id(post.getPostId())
			.title(post.getTitle())
			.content(post.getContent())
			.name(post.getName())
			.views(post.getViews())
			.build();
	}
}
