package com.sctk.cmc.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class SizesByPart {
    private float height;
    private float upper;
    private float lower;
    private float weight;
    private float shoulder;
    private float chest;
    private float waist;
    private float hip;
    private float thigh;

    public static SizesByPart getEmpty() {
        return new SizesByPart(0,0,0,0,0,0,0,0,0);
    }
}


