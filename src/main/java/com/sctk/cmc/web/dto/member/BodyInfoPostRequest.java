package com.sctk.cmc.web.dto.member;

import com.sctk.cmc.domain.SizesByPart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BodyInfoPostRequest {
    private SizesByPart sizes;
}
