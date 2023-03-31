package com.sctk.cmc.domain;

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
}
