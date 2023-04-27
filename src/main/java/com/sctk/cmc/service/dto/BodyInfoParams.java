package com.sctk.cmc.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BodyInfoParams {
    private float height;
    private float upper;
    private float lower;
    private float weight;
    private float shoulder;
    private float chest;
    private float waist;
    private float hip;
    private float thigh;

    @Builder
    public BodyInfoParams(float height, float upper, float lower, float weight, float shoulder, float chest, float waist, float hip, float thigh) {
        this.height = height;
        this.upper = upper;
        this.lower = lower;
        this.weight = weight;
        this.shoulder = shoulder;
        this.chest = chest;
        this.waist = waist;
        this.hip = hip;
        this.thigh = thigh;
    }
}
