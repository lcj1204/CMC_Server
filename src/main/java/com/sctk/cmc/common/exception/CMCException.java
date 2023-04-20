package com.sctk.cmc.common.exception;

import lombok.Getter;

@Getter
public class CMCException extends RuntimeException {
    private ResponseStatus status;

    public CMCException(ResponseStatus status) {
        super(status.name());
        this.status = status;
    }
}
