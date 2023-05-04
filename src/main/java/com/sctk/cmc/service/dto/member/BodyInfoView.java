package com.sctk.cmc.service.dto.member;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class BodyInfoView {
    private float height;
    private float weight;
    private float shoulder;
    private float chest;
    private float waist;
    private float hip;
    private float thigh;
    private float upper;
    private float lower;

    @Builder
    public BodyInfoView(float height, float weight, float shoulder, float chest, float waist, float hip, float thigh, float upper, float lower) {
        this.height = height;
        this.weight = weight;
        this.shoulder = shoulder;
        this.chest = chest;
        this.waist = waist;
        this.hip = hip;
        this.thigh = thigh;
        this.upper = upper;
        this.lower = lower;
    }
}
