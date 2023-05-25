package com.sctk.cmc.domain;

import com.sctk.cmc.service.dto.custom.CustomParams;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Custom extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custom_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "designer_id")
    private Designer designer;

    private String highCategory;
    private String lowCategory;

    private String title;
    private Integer desiredPrice;
    private String requirement;

    @OneToOne(mappedBy = "custom")
    private CustomReference reference;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "custom_result_id")
    private CustomResult customResult;

    @Enumerated(EnumType.STRING)
    private CustomStatus accepted;
    private Boolean active;

    @Builder
    public Custom(Member member, Designer designer, String highCategory, String lowCategory, String title,
                  Integer desiredPrice, String requirement, CustomReference reference, CustomResult customResult,
                  CustomStatus accepted, Boolean active) {
        this.member = member;
        this.designer = designer;
        this.highCategory = highCategory;
        this.lowCategory = lowCategory;
        this.title = title;
        this.desiredPrice = desiredPrice;
        this.requirement = requirement;
        this.reference = reference;
        this.customResult = customResult;
        this.accepted = accepted;
        this.active = active;
    }

    public static Custom create(Member member, Designer designer, CustomParams customParams) {
        return Custom.builder()
                .member(member)
                .designer(designer)
                .highCategory(customParams.getHighCategory())
                .lowCategory(customParams.getLowCategory())
                .title(customParams.getTitle())
                .desiredPrice(customParams.getDesiredPrice())
                .requirement(customParams.getRequirement())
                // 이미지 빠짐
                .accepted(CustomStatus.REQUESTING)
                .active(true)
                .build();
    }

    public void changeActiveToFalse() {
        this.active = false;
    }
}