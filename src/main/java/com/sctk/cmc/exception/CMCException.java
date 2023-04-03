package com.sctk.cmc.exception;

import lombok.Getter;

@Getter
public class CMCException extends RuntimeException {
    private ResponseStatus status;

    public CMCException(ResponseStatus status) {
        super(status.name());
        this.status = status;
    }
}
