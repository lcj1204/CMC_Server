package com.sctk.cmc.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class LikeDesigner extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_designer_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "designer_id")
    private Designer designer;

    public LikeDesigner(Member member, Designer designer) {
        this.member = member;
        member.addLike(this);

        this.designer = designer;
        designer.addMemberLike(this);
    }

    public void remove() {
        this.member.cancelLike(this);
        this.designer.removeMemberLike(this);
    }
}
