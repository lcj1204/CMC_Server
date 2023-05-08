package com.sctk.cmc.dto.designer;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DesignerLoginParam {
    private String email;
    private String password;
}
