package cotato.backend.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	//400
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.", "COMMON-001"),
	INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "요청 파라미터가 잘못 되었습니다.", "COMMON-002"),

	// 404 - Not Found
	RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "리소스를 찾을 수 없습니다.", "POST-001"),
	POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다.", "POST-002"),

	// 400 - Validation Errors
	POST_VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "게시글 작성에 실패했습니다. 입력값을 확인해주세요.", "POST-004"),

	//500
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부에서 에러가 발생하였습니다.", "COMMON-002"),

	// 403 - Forbidden
	UNAUTHORIZED_ACCESS(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.", "POST-005");

	private final HttpStatus httpStatus;
	private final String message;
	private final String code;
}
