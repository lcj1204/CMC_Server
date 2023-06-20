package com.sctk.cmc.domain;

import com.sctk.cmc.common.exception.CMCException;
import lombok.Getter;

import java.util.Arrays;

import static com.sctk.cmc.common.exception.ResponseStatus.PRODUCTION_PROGRESS_ILLEGAL_TYPE;

@Getter
public enum ProgressType {
    ACCEPT("ACCEPT", 1), DESIGN("DESIGN", 2), SAMPLE("SAMPLE", 3), COMPLETION("COMPLETION", 4);

    private String name;
    private int priority;

    ProgressType(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    public static ProgressType convertedFromString(String progressType) {
        return Arrays.stream(ProgressType.values())
                .filter(type -> type.name().equals(progressType))
                .findFirst()
                .orElseThrow(() -> new CMCException(PRODUCTION_PROGRESS_ILLEGAL_TYPE));
    }
}
