package com.sctk.cmc.service.member.dto;

import com.sctk.cmc.domain.SizesByPart;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BodyInfoModifyParams {
    private SizesByPart sizes;
}
