package com.sctk.cmc.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class BodyInfo extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "body_info_id")
    private Long id;

    @Embedded
    private SizesByPart sizesByPart;

    @OneToOne(mappedBy = "bodyInfo", fetch = FetchType.LAZY)
    private Member member;

    // Constructor
    @Builder
    public BodyInfo(Member member, SizesByPart sizesByPart) {
        this.member = member;
        member.setBodyInfo(this);
        this.sizesByPart = sizesByPart;
    }
}
