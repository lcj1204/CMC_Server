package com.sctk.cmc.domain;

import com.sctk.cmc.domain.likeobject.LikeDesigner;
import com.sctk.cmc.domain.likeobject.LikeProduct;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String name;
    private String nickname;
    private String email;
    private String password;
    private String profileImgUrl;
    private String introduce;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LikeDesigner> designerLikes = new HashSet<>();

    private int designerLikeCount;
    private int productLikeCount;
    private Boolean active;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "body_info_id")
    private BodyInfo bodyInfo;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LikeProduct> productLikes = new HashSet<>();

    @OneToMany(mappedBy = "member")
    private List<Custom> custom = new ArrayList<>();


    @Builder
    public Member(String name, String nickname, String email, String password, String introduce, String profileImgUrl) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.introduce = introduce;
        this.profileImgUrl = profileImgUrl;
        this.designerLikeCount = 0;
        this.productLikeCount = 0;
        this.role = Role.MEMBER;
        active = true;
    }

    public void setBodyInfo(BodyInfo bodyInfo) {
        this.bodyInfo = bodyInfo;
    }

    public void addDesignerLike(LikeDesigner like) {
        this.designerLikes.add(like);
        this.designerLikeCount++;
    }

    public void addProductLike(LikeProduct like) {
        this.productLikes.add(like);
        this.productLikeCount++;
    }

    public void cancelDesignerLike(LikeDesigner likeDesigner) {
        this.designerLikes.remove(likeDesigner);
        this.designerLikeCount--;
    }

    public void cancelProductLike(LikeProduct likeProduct) {
        this.productLikes.remove(likeProduct);
        this.productLikeCount--;
    }

    public void setProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }
}