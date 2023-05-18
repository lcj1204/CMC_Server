package com.sctk.cmc.domain;

import com.sctk.cmc.common.exception.CMCException;
import com.sctk.cmc.common.exception.ResponseStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.*;

import static com.sctk.cmc.common.exception.ResponseStatus.*;

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

    private int likeCount;
    private Boolean active;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "body_info_id")
    private BodyInfo bodyInfo;

    @OneToMany(mappedBy = "member")
    private List<LikeProduct> likeProducts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ProductionRequest> productionRequest = new ArrayList<>();


    @Builder
    public Member(String name, String nickname, String email, String password, String introduce) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.introduce = introduce;
        this.likeCount = 0;
        this.role = Role.MEMBER;
        active = true;
    }

    public void setBodyInfo(BodyInfo bodyInfo) {
        this.bodyInfo = bodyInfo;
    }

    public void addLike(LikeDesigner like) {
        this.designerLikes.add(like);
        this.likeCount++;
    }

    public void cancelLike(LikeDesigner likeDesigner) {
        this.designerLikes.remove(likeDesigner);
        this.likeCount--;
    }
}