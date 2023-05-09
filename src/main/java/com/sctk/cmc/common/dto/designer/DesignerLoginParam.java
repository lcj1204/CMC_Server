package com.sctk.cmc.common.dto.designer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DesignerLoginParam {
    private String email;
    private String password;
}
