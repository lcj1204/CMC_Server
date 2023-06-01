package com.sctk.cmc.service.member.dto;

import com.sctk.cmc.domain.SizesByPart;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BodyInfoView {
    private SizesByPart sizes;
}
