package com.sctk.cmc.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String name;
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
    private List<LikeProduct> likeProducts = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DescriptionImg> imgs = new ArrayList<>();

    @Builder
    public Product(String name, int price, String tag, String description, int likeCount, Designer designer) {
        this.name = name;
        this.price = price;
        this.tag = tag;
        this.description = description;
        this.likeCount = likeCount;
        this.designer = designer;
    }

    public static Product create(Designer designer, String name, int price, String tag, String description) {
        return Product.builder()
                .designer(designer)
                .name(name)
                .price(price)
                .tag(tag)
                .description(description)
                .likeCount(0)
                .build();
    }
}
