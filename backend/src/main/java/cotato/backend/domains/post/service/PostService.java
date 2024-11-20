package cotato.backend.domains.post.service;

import static cotato.backend.common.exception.ErrorCode.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cotato.backend.common.excel.ExcelUtils;
import cotato.backend.common.exception.ApiException;
import cotato.backend.domains.post.domain.Post;
import cotato.backend.domains.post.dto.request.AddPostRequest;
import cotato.backend.domains.post.dto.response.PostResponse;
import cotato.backend.domains.post.repository.PostRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Transactional
public class PostService {

	static final int PAGE_SIZE = 10;

	private final PostRepository postRepository;

	// 로컬 파일 경로로부터 엑셀 파일을 읽어 Post 엔터티로 변환하고 저장
	public void saveEstatesByExcel(String filePath) {
		try {
			// 엑셀 파일을 읽어 데이터 프레임 형태로 변환
			List<Post> posts = ExcelUtils.parseExcelFile(filePath).stream()
				.map(row -> {
					String title = row.get("title");
					String content = row.get("content");
					String name = row.get("name");

					return Post.builder()
						.title(title)
						.content(content)
						.name(name)
						.build();
				})
				.collect(Collectors.toList());

			// 리스트 전체 저장
			postRepository.saveAll(posts);

		} catch (Exception e) {
			log.error("Failed to save estates by excel", e);
			throw ApiException.from(INTERNAL_SERVER_ERROR);
		}

	}

	public void savePost(AddPostRequest request) {
		postRepository.save(request.toEntity());
	}

	public PostResponse getPost(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> ApiException.from(POST_NOT_FOUND));
		post.updateViews();

		return PostResponse.from(post);
	}

	public void deletePost(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> ApiException.from(POST_NOT_FOUND));

		postRepository.delete(post);
	}

	public List<PostResponse> getAllPost() {
		List<Post> posts = postRepository.findAll();

		List<PostResponse> postResponses = posts.stream().map(PostResponse::from).toList();

		return postResponses;
	}

	public Page<PostResponse> getPostsByViews(int pageNo, String criteria) {

		Pageable pageable = PageRequest.of(pageNo, 10, Sort.by(Sort.Direction.DESC, criteria));

		Page<PostResponse> page = postRepository.findAll(pageable).map(PostResponse::from);

		return page;
	}

}
