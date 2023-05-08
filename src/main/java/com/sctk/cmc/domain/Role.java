package com.sctk.cmc.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    MEMBER("ROLE_MEMBER"),
    DESIGNER("ROLE_DESIGNER");

    private final String roleName;
}
