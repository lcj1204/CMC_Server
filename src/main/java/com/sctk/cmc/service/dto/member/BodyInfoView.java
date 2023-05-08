package com.sctk.cmc.service.dto.member;

import com.sctk.cmc.domain.SizesByPart;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BodyInfoView {
    private SizesByPart sizes;
}
