package com.sctk.cmc.domain.likeobject;

import com.sctk.cmc.domain.BaseTimeEntity;
import com.sctk.cmc.domain.Designer;
import com.sctk.cmc.domain.LikedEntity;
import com.sctk.cmc.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class LikeDesigner extends BaseTimeEntity implements LikeObject {
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
        member.addDesignerLike(this);

        this.designer = designer;
        designer.addMemberLike(this);
    }

    @Override
    public void remove() {
        this.member.cancelDesignerLike(this);
        this.designer.removeMemberLike(this);
    }

    @Override
    public LikedEntity getObject() {
        return this.designer;
    }
}
