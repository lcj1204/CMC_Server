package com.sctk.cmc.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Designer extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "designer_id")
    private Long id;
    private String name;
    private String nickname;
    private String email;
    private String password;

    @OneToOne(mappedBy = "designer")
    private Portfolio portfolio;
    private String introduce;
    private String contact; // Contact 클래스 필요
    private int likeCount;
    private Boolean active;

    @Builder
    public Designer(String name, String nickname, String email, String password, String introduce, String contact) {
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