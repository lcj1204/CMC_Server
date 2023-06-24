package com.sctk.cmc.domain;

import com.sctk.cmc.domain.likeobject.LikeProduct;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity implements LikedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String name;
    private String highCategory;
    private String lowCategory;
    private int price;
    private String tag;
    private String description;
//    private LocalTime releaseAt;
    private int likeCount;
//    @Enumerated(EnumType.STRING)
//    private ProductStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "designer_id")
    private Designer designer;

//    @OneToMany(mappedBy = "product")
//    private List<Size> sizes = new ArrayList<>();
    @OneToMany(mappedBy = "product")
    private Set<LikeProduct> likeProducts = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DescriptionImg> descriptionImgList = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ThumbnailImg> thumbnailImgList = new ArrayList<>();

    @Builder
    public Product(String name, String highCategory, String lowCategory,
                   int price, String tag, String description, int likeCount, Designer designer) {
        this.name = name;
        this.highCategory = highCategory;
        this.lowCategory = lowCategory;
        this.price = price;
        this.tag = tag;
        this.description = description;
        this.likeCount = likeCount;
        this.designer = designer;
    }

    public static Product create(Designer designer, String name, String highCategory, String lowCategory,
                                 int price, String tag, String description) {
        return Product.builder()
                .designer(designer)
                .name(name)
                .highCategory(highCategory)
                .lowCategory(lowCategory)
                .price(price)
                .tag(tag)
                .description(description)
                .likeCount(0)
                .build();
    }

    public void addMemberLike(LikeProduct likeProduct) {
        this.likeProducts.add(likeProduct);
        this.likeCount++;
    }

    public void removeMemberLike(LikeProduct like) {
        this.likeProducts.remove(like);
        likeCount--;
    }
}
