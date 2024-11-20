package cotato.backend.domains.post.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cotato.backend.common.dto.DataResponse;
import cotato.backend.domains.post.dto.request.AddPostRequest;
import cotato.backend.domains.post.dto.response.PostResponse;
import cotato.backend.domains.post.service.PostService;
import cotato.backend.domains.post.dto.request.SavePostsByExcelRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Tag(name = "게시글 API", description = "게시글 관리와 관련된 API입니다.")
public class PostController {

	private final PostService postService;

	/**
	 * 엑셀 파일을 통해 게시글 저장
	 *
	 * @param request 엑셀 파일 경로를 포함한 요청 객체
	 * @return 게시글 저장 성공 여부
	 */
	@Operation(summary = "엑셀 파일로 게시글 저장", description = "제공된 엑셀 파일 경로를 사용하여 게시글을 저장합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "게시글 저장 성공"),
		@ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
		@ApiResponse(responseCode = "500", description = "서버 오류")
	})
	@PostMapping("/excel")
	public ResponseEntity<DataResponse<Void>> savePostsByExcel(@RequestBody SavePostsByExcelRequest request) {
		postService.saveEstatesByExcel(request.getPath());

		return ResponseEntity.ok(DataResponse.ok());
	}

	/**
	 * 새로운 게시글 저장
	 *
	 * @param request 게시글 정보를 포함한 요청 객체
	 * @return 게시글 저장 성공 여부
	 */
	@Operation(summary = "게시글 저장", description = "새로운 게시글을 저장합니다.")
	@PostMapping
	public ResponseEntity<DataResponse<Void>> savePost(@RequestBody AddPostRequest request) {
		postService.savePost(request);

		return ResponseEntity.ok(DataResponse.ok());
	}

	/**
	 * 모든 게시글 조회
	 *
	 * @return 저장된 모든 게시글 목록
	 */
	@Operation(summary = "게시글 목록 조회", description = "저장된 모든 게시글을 조회합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "게시글 조회 성공"),
		@ApiResponse(responseCode = "500", description = "서버 오류")
	})
	@GetMapping("/v1")
	public ResponseEntity<DataResponse<List<PostResponse>>> getAllPost() {
		List<PostResponse> posts = postService.getAllPost();

		return ResponseEntity.ok(DataResponse.from(posts));
	}


	/**
	 * 조회 수 기준으로 게시글 목록 조회
	 *
	 * @param pageable 페이지네이션 정보를 포함하는 객체
	 *                 - size: 페이지당 데이터 개수 (기본값: 10)
	 *                 - sort: 정렬 기준 (기본값: views, 내림차순)
	 *                 - page: 요청할 페이지 번호 (기본값: 0)
	 * @return 페이지네이션 정보를 포함한 게시글 목록
	 */
	@Operation(summary = "게시글 목록 조회 (조회순, 페이징 포함)", description = "조회 수 기준으로 정렬된 게시글 목록을 페이징 처리하여 반환합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "게시글 조회 성공"),
		@ApiResponse(responseCode = "500", description = "서버 오류")
	})
	@GetMapping("/v2")
	public ResponseEntity<DataResponse<Page<PostResponse>>> getAllPostByViews(
		@RequestParam(required = true, defaultValue = "0", value="page") int pageNo,
		@RequestParam(required = true, defaultValue = "views", value="criteria") String criteria
		) {
		Page<PostResponse> postsByViews = postService.getPostsByViews(pageNo,criteria);

		return ResponseEntity.ok(DataResponse.from(postsByViews));
	}

	/**
	 * 특정 게시글 조회
	 *
	 * @param id 조회할 게시글의 ID
	 * @return 게시글 정보
	 */
	@Operation(summary = "특정 게시글 조회", description = "ID를 통해 특정 게시글을 조회합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "게시글 조회 성공"),
		@ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음"),
		@ApiResponse(responseCode = "500", description = "서버 오류")
	})
	@GetMapping("{id}")
	public ResponseEntity<DataResponse<PostResponse>> getPost(@PathVariable Long id) {
		PostResponse post = postService.getPost(id);

		return ResponseEntity.ok(DataResponse.from(post));
	}

	/**
	 * 특정 게시글 삭제
	 *
	 * @param id 삭제할 게시글의 ID
	 * @return 게시글 삭제 성공 여부
	 */
	@Operation(summary = "게시글 삭제", description = "ID를 통해 특정 게시글을 삭제합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "게시글 삭제 성공"),
		@ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음"),
		@ApiResponse(responseCode = "500", description = "서버 오류")
	})
	@DeleteMapping("{id}")
	public ResponseEntity<DataResponse<Void>> deletePost(@PathVariable Long id) {
		postService.deletePost(id);

		return ResponseEntity.ok(DataResponse.ok());
	}
}
