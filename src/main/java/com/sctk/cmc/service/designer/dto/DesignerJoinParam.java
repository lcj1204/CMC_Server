package com.sctk.cmc.service.designer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DesignerJoinParam {
    private String name;
    private String nickname;
    private String email;
    private String password;
    private String contact;
}
