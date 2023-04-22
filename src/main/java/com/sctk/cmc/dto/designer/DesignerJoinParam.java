package com.sctk.cmc.dto.designer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DesignerJoinParam {
    private String name;
    private String nickname;
    private String email;
    private String password;
    private String contact;
}
