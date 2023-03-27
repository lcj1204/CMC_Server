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
    private String introduce;
    private String contact; // Contact 클래스 필요

    @OneToMany(mappedBy = "member")
    private List<LikeDesigner> designerLikes;

    private int likeCount;
    private Boolean active;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "body_info_id")
    private BodyInfo bodyInfo;

    @OneToMany(mappedBy = "member")
    private List<LikeProduct> likeProducts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private ProductionRequest productionRequest;

    @Builder
    public Member(String name, String nickname, String email, String password, String introduce, String contact) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.introduce = introduce;
        this.contact = contact;
        this.likeCount = 0;
        active = true;
    }
}