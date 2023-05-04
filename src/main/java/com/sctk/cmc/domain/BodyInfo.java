package com.sctk.cmc.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class BodyInfo extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "body_info_id")
    private Long id;
    private float height;
    private float upper;
    private float lower;
    private float weight;
    private float shoulder;
    private float chest;
    private float waist;
    private float hip;
    private float thigh;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // Constructor
    @Builder
    public BodyInfo(Member member, float height, float upper, float lower, float weight, float shoulder, float chest, float waist, float hip, float thigh) {
        this.member = member;
        member.setBodyInfo(this);
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
