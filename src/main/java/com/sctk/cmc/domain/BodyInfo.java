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

    @Embedded
    private SizesByPart sizes;

    @OneToOne(mappedBy = "bodyInfo", fetch = FetchType.LAZY)
    private Member member;

    // Constructor
    @Builder
    public BodyInfo(Member member, SizesByPart sizes) {
        this.member = member;
        member.setBodyInfo(this);
        this.sizes = sizes;
    }
}
