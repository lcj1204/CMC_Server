package com.sctk.cmc.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
public class RequestReference extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_reference_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_request_id")
    private ProductionRequest productionRequest;

    @OneToMany(mappedBy = "reference")
    private List<RequestReferenceImg> referenceImgs;

    public RequestReference(ProductionRequest productionRequest) {
        this.productionRequest = productionRequest;
        this.referenceImgs = new ArrayList<>();
    }
}