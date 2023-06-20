package com.sctk.cmc.domain;

public enum ProgressType {
    ACCEPT("ACCEPT"), DESIGN("DESIGN"), SAMPLE("SAMPLE"), COMPLETION("COMPLETION");

    private String name;
    ProgressType(String name) {
        this.name = name;
    }
}
