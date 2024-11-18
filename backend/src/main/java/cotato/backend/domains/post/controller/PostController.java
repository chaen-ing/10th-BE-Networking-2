package cotato.backend.domains.post.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cotato.backend.common.dto.DataResponse;
import cotato.backend.domains.post.dto.request.AddPostRequest;
import cotato.backend.domains.post.dto.response.PostResponse;
import cotato.backend.domains.post.service.PostService;
import cotato.backend.domains.post.dto.request.SavePostsByExcelRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@PostMapping("/excel")
	public ResponseEntity<DataResponse<Void>> savePostsByExcel(@RequestBody SavePostsByExcelRequest request) {
		postService.saveEstatesByExcel(request.getPath());

		return ResponseEntity.ok(DataResponse.ok());
	}

	@PostMapping
	public ResponseEntity<DataResponse<Void>> savePost(@RequestBody AddPostRequest request) {
		postService.savePost(request);

		return ResponseEntity.ok(DataResponse.ok());
	}

	@GetMapping("{id}")
	public ResponseEntity<DataResponse<PostResponse>> getPost(@PathVariable Long id) {
		PostResponse post = postService.getPost(id);

		return ResponseEntity.ok(DataResponse.from(post));
	}

	@DeleteMapping("{id}")
	public ResponseEntity<DataResponse<Void>> deletePost(@PathVariable Long id) {
		postService.deletePost(id);

		return ResponseEntity.ok(DataResponse.ok());
	}
}
