package com.sctk.cmc.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "member")
    private List<LikeDesigner> designerLikes;

    private int likeCount;
    private Boolean active;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, orphanRemoval = true)
    private BodyInfo bodyInfo;

    @OneToMany(mappedBy = "member")
    private List<LikeProduct> likeProducts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ProductionRequest> productionRequest;

    @Builder
    public Member(String name, String nickname, String email, String password, String introduce) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.introduce = introduce;
        this.likeCount = 0;
        active = true;
    }

    public void setBodyInfo(BodyInfo bodyInfo) {
        this.bodyInfo = bodyInfo;
    }
}