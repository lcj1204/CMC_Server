package com.sctk.cmc.domain;

import com.sctk.cmc.common.exception.CMCException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.sctk.cmc.common.exception.ResponseStatus.DESIGNERS_HIGH_CATEGORY_MORE_THAN_LIMIT;
import static com.sctk.cmc.common.exception.ResponseStatus.DESIGNERS_LOW_CATEGORY_MORE_THAN_LIMIT;

@Getter
@NoArgsConstructor
@Entity
public class HighCategory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "high_category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "designer_id")
    private Designer designer;

    @OneToMany(mappedBy = "highCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LowCategory> lowCategories = new ArrayList<>();

    private String name;

    public HighCategory(Designer designer, String name) {
        this.designer = designer;
        designer.addHighCategory(this);
        this.name = name;
    }

    public void addLowCategory(LowCategory lowCategory) {
        if (this.lowCategories.size() >= 3) {
            throw new CMCException(DESIGNERS_LOW_CATEGORY_MORE_THAN_LIMIT);
        }

        this.lowCategories.add(lowCategory);
    }

    public List<String> getLowCategoryNames() {
        return lowCategories.stream()
                .map(lowCategory -> lowCategory.getName())
                .collect(Collectors.toUnmodifiableList());
    }
}
