package cotato.backend.domains.post.dto.request;

import cotato.backend.domains.post.domain.Post;

public record AddPostRequest(
	String title,
	String content,
	String name

) {
	public Post toEntity(){
		return Post.builder()
			.title(title)
			.content(content)
			.name(name)
			.build();
	}
}
