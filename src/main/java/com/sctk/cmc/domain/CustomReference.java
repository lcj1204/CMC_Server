package com.sctk.cmc.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CustomReference extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custom_reference_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custom_id")
    private Custom custom;

    @OneToMany(mappedBy = "reference", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomReferenceImg> referenceImgs = new ArrayList<>();

    @Builder
    public CustomReference(Custom custom) {
        this.custom = custom;
        if (custom != null) {
            custom.setReference(this);
        }
    }

    public static CustomReference create(Custom createdcCustom) {
        return CustomReference.builder()
                .custom(createdcCustom)
                .build();
    }
}