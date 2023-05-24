package com.sctk.cmc.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
public class CustomReference extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custom_reference_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custom_id")
    private Custom custom;

    @OneToMany(mappedBy = "reference")
    private List<CustomReferenceImg> referenceImgs;

    public CustomReference(Custom custom) {
        this.custom = custom;
        this.referenceImgs = new ArrayList<>();
    }
}