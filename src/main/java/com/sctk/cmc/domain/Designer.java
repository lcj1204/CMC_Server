package com.sctk.cmc.domain;

import com.sctk.cmc.common.exception.CMCException;
import com.sctk.cmc.domain.likeobject.LikeDesigner;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

import static com.sctk.cmc.common.exception.ResponseStatus.*;

@Getter
@NoArgsConstructor
@Entity
public class Designer extends BaseTimeEntity implements LikedEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "designer_id")
    private Long id;
    private String name;
    private String nickname;
    private String email;
    private String password;
    private String profileImgUrl;

    @OneToOne(mappedBy = "designer", cascade = CascadeType.ALL)
    private Portfolio portfolio;

    @OneToMany(mappedBy = "designer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HighCategory> highCategories = new ArrayList<>();

    @OneToMany(mappedBy = "designer")
    private List<LowCategory> lowCategories = new ArrayList<>();
    private String introduce;
    private String contact;

    @OneToMany(mappedBy = "designer")
    private Set<LikeDesigner> memberLikes = new HashSet<>();

    private int likeCount;
    private Boolean active;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Designer(String name, String nickname, String email, String password, String introduce, String profileImgUrl, String contact) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.introduce = introduce;
        this.contact = contact;
        this.profileImgUrl = profileImgUrl;
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

    public List<String> getHighCategoryNames() {
        return highCategories.stream()
                .map(category -> category.getName())
                .collect(Collectors.toUnmodifiableList());
    }

    public List<String> getLowCategoryNames() {
        return lowCategories.stream()
                .map(category -> category.getName())
                .collect(Collectors.toUnmodifiableList());
    }

    // Setter
    public void addHighCategory(HighCategory highCategory) {
        if (this.highCategories.size() >= 3) {
            throw new CMCException(DESIGNERS_HIGH_CATEGORY_MORE_THAN_LIMIT);
        }

        this.highCategories.add(highCategory);
    }

    public void addLowCategory(LowCategory lowCategory) {
        this.lowCategories.add(lowCategory);
    }

    public void addMemberLike(LikeDesigner like) {
        this.memberLikes.add(like);
        likeCount++;
    }

    public void removeMemberLike(LikeDesigner like) {
        this.memberLikes.remove(like);
        likeCount--;
    }

    public void setProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public void clearCategories() {
        highCategories.clear();
        lowCategories.clear();
    }

    public boolean containsNameInCategories(String name) {
        return highCategories.stream()
                .anyMatch(highCategory -> highCategory.getName().toUpperCase().contains(name))
                ||
                lowCategories.stream()
                .anyMatch(lowCategory -> lowCategory.getName().toUpperCase().contains(name));
    }
}