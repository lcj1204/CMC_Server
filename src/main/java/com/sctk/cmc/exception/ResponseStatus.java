package com.sctk.cmc.exception;

public enum ResponseStatus {
    SUCCESS(1000, "응답에 성공하였습니다."),
    AUTHENTICATION_ILLEGAL_EMAIL(1001, "존재하지 않는 이메일입니다."),
    AUTHENTICATION_DUPLICATE_EMAIL(1002, "중복된 이메일입니다."),
    AUTHENTICATION_ILLEGAL_PASSWORD(1003, "비밀번호가 일치하지 않습니다.");

    private int code;
    private String message;

    ResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
