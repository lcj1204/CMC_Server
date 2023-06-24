package com.sctk.cmc.common.exception;

import lombok.Getter;

@Getter
public enum ResponseStatus {
    // Common
    SUCCESS(1000, "응답에 성공하였습니다."),
    AUTHENTICATION_ILLEGAL_EMAIL(1001, "존재하지 않는 이메일입니다."),
    AUTHENTICATION_DUPLICATE_EMAIL(1002, "중복된 이메일입니다."),
    AUTHENTICATION_ILLEGAL_PASSWORD(1003, "비밀번호가 일치하지 않습니다."),
    INTERNAL_SERVER_ERROR(1004, "서버 내부 오류 입니다."),
    NOT_SUPPORTED_LIKING(1005, "지원하지 않는 좋아요입니다."),
    SEARCH_KEYWORD_LENGTH_TOO_SHORT(1006, "검색어는 최소 두 글자 이상이어야 합니다."),
    INVALID_DATE(1007, "시작날짜가 끝날짜보다 빨라야 합니다."),

    // Members
    MEMBERS_ILLEGAL_ID(2001, "존재하지 않는 회원 ID입니다."),
    MEMBERS_ILLEGAL_EMAIL(2002, "존재하지 않는 회원 이메일입니다."),
    MEMBERS_ALREADY_LIKING_DESIGNER(2003, "이미 좋아요 처리가 된 디자이너입니다."),
    MEMBERS_NOT_LIKED_DESIGNER(2004, "좋아요 처리한 적이 없는 디자이너입니다."),
    MEMBERS_EMPTY_BODY_INFO(2005, "신체 정보가 등록되지 않았습니다."),
    MEMBERS_NOT_OWNER(2006, "해당 회원의 소유가 아닙니다."),

    // Designers
    DESIGNERS_ILLEGAL_ID(3001, "존재하지 않는 디자이너 ID입니다."),
    DESIGNERS_HIGH_CATEGORY_MORE_THAN_LIMIT(3002, "등록할 수 있는 카테고리는 최대 3개입니다."),
    DESIGNERS_LOW_CATEGORY_MORE_THAN_LIMIT(3003, "한 카테고리에 등록할 수 있는 소재는 최대 3개입니다."),
    DESIGNERS_NON_EXISTING_CRITERIA(3004, "디자이너 검색에 존재하지 않는 기준입니다."),
    NOT_HAVE_DESIGNERS_AUTHORITY(3004, "디자이너 권한이 없습니다."),
    DESIGNERS_EMPTY_CATEGORY(3005, "등록된 카테고리가 없습니다."),
    DESIGNERS_EMPTY_PORTFOLIO(3006, "등록된 포트폴리오가 없습니다."),

    // jwt
    INVALID_TOKEN(4000, "잘못된 Token 입니다."),
    EXPIRED_REFRESH_TOKEN(4001, "만료된 Refresh Token 입니다."),
    INCONSISTENCY_REFRESH_TOKEN(4002, "Refresh Token 이 일치하지 않습니다."),

    // redis
    INVALID_ROLE(4003, "잘못된 ROLE 입니다."),

    // custom
    CUSTOM_ILLEGAL_ID(5000, "존재 하지 않는 custom ID 입니다."),
    ALREADY_RESPONDED_CUSTOM(5001, "이미 응답한 custom 요청 입니다."),
    CUSTOM_RESULT_ILLEGAL_ID(5002, "존재 하지 않는 customResult ID 입니다."),
    CUSTOM_RESULT_UNMATCH_ID(5003, "일치 하지 않는 customResult ID 입니다."),
    NOT_APPROVAL_CUSTOM_RESULT(5004, "승인된 Custom 이 아닙니다."),
    NOT_REFUSAL_CUSTOM_RESULT(5005, "거절된 Custom 이 아닙니다."),

    // AWS
    AWS_FILE_NOT_FOUND(6000, "AWS에 존재하지 않는 파일입니다."),
    S3_TEMP_FILE_CONVERT_FAIL(6001, "이미지를 임시 파일로 변환하는데 실패했습니다."),

    // Production Progress
    PRODUCTION_PROGRESS_ILLEGAL_ID(7000, "존재 하지 않는 Production Progress 입니다."),
    PRODUCTION_PROGRESS_ILLEGAL_TYPE(7001, "존재 하지 않는 Production Progress 이미지 타입 입니다."),

    // Product
    PRODUCT_ILLEGAL_ID(8000, "존재 하지 않는 Product 입니다.");


    private int code;
    private String message;

    ResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
