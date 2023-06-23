package com.sctk.cmc.domain.likeobject;

import com.sctk.cmc.domain.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class LikeProduct extends BaseTimeEntity implements LikeObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_mp_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    // Constructor

    public LikeProduct(Member member, Product product) {
        this.member = member;
        member.addProductLike(this);

        this.product = product;
        product.addMemberLike(this);
    }

    @Override
    public void remove() {
        this.member.cancelProductLike(this);
        this.product.removeMemberLike(this);
    }

    @Override
    public LikedEntity getObject() {
        return this.product;
    }

}
