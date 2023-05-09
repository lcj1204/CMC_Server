package com.sctk.cmc.domain;

import com.sctk.cmc.common.exception.CMCException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.sctk.cmc.common.exception.ResponseStatus.*;

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
    private String profileImgUrl;

    @OneToOne(mappedBy = "designer")
    private Portfolio portfolio;

    @OneToMany(mappedBy = "designer", cascade = CascadeType.ALL)
    private List<HighCategory> highCategories = new ArrayList<>();

    @OneToMany(mappedBy = "designer")
    private List<LowCategory> lowCategories = new ArrayList<>();
    private String introduce;
    private String contact;

    @OneToMany(mappedBy = "designer")
    private List<LikeDesigner> memberLikes;

    private int likeCount;
    private Boolean active;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Designer(String name, String nickname, String email, String password, String introduce, String contact) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.introduce = introduce;
        this.contact = contact;
        this.likeCount = 0;
        this.role = Role.DESIGNER;
        active = true;
    }

    // Getter
    public List<HighCategory> getHighCategories() {
        return Collections.unmodifiableList(this.highCategories);
    }

    public List<LowCategory> getLowCategories() {
        return Collections.unmodifiableList(this.lowCategories);
    }

    // Setter
    public void addHighCategory(HighCategory highCategory) {
        this.highCategories.add(highCategory);
    }

    public void addLowCategory(LowCategory lowCategory) {
        this.lowCategories.add(lowCategory);
    }
}