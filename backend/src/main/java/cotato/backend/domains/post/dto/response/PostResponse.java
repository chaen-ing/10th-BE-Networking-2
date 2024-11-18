package cotato.backend.domains.post.dto.response;

import cotato.backend.domains.post.domain.Post;
import lombok.Builder;

@Builder
public record PostResponse(
	String title,
	String content,
	String name,
	Long views
) {
	public static PostResponse from(Post post) {
		return PostResponse.builder()
			.title(post.getTitle())
			.content(post.getContent())
			.name(post.getName())
			.views(post.getViews())
			.build();
	}
}
