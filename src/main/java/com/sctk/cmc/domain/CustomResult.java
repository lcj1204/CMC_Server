package com.sctk.cmc.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class CustomResult extends BaseTimeEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custom_result_id")
    private Long id;

    @OneToOne(mappedBy = "custom_result_id")
    private Custom custom;

    private LocalDate expectStartDate;

    private LocalDate expectEndDate;

    private int expectPrice;

    @Column(columnDefinition = "TEXT")
    private String message;

}
