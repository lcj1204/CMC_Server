package com.sctk.cmc.domain;

import com.sctk.cmc.common.exception.CMCException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "designer")
    private List<HighCategory> highCategories = new ArrayList<>();

    @OneToMany(mappedBy = "designer")
    private List<LowCategory> lowCategories = new ArrayList<>();
    private String introduce;
    private String contact;

    @OneToMany(mappedBy = "designer")
    private List<LikeDesigner> memberLikes;

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

    public void setHighCategories(List<HighCategory> highCategories) {
        if (highCategories.size() > 3) {
            throw new CMCException(DESIGNERS_HIGH_CATEGORY_MORE_THAN_LIMIT);
        }
        this.highCategories = highCategories;
        highCategories.stream()
                .forEach(category -> category.setDesigner(this));
    }

    public void setLowCategories(List<LowCategory> lowCategories) {
        if (lowCategories.size() > 3) {
            throw new CMCException(DESIGNERS_LOW_CATEGORY_MORE_THAN_LIMIT);
        }
        this.lowCategories = lowCategories;
        lowCategories.stream()
                .forEach(category -> category.setDesigner(this));
    }
}