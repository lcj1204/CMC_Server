package com.sctk.cmc.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommonUser {
    private String role;
    private String email;
}
