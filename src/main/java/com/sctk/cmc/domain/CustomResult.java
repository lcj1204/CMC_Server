package com.sctk.cmc.domain;

import com.sctk.cmc.service.dto.customResult.CustomResultAcceptParams;
import com.sctk.cmc.service.dto.customResult.CustomResultRejectParams;
import lombok.Builder;
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

    @OneToOne(mappedBy = "customResult")
    private Custom custom;

    private LocalDate expectStartDate;

    private LocalDate expectEndDate;

    private int expectPrice;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Builder
    public CustomResult(Custom custom, LocalDate expectStartDate, LocalDate expectEndDate, int expectPrice, String message) {
        this.custom = custom;
        if (custom != null) {
            custom.addCustomResult(this);
        }
        this.expectStartDate = expectStartDate;
        this.expectEndDate = expectEndDate;
        this.expectPrice = expectPrice;
        this.message = message;
    }

    public static CustomResult ofAcceptance(Custom custom, CustomResultAcceptParams customResultAcceptParams) {
        return CustomResult.builder()
                .custom(custom)
                .expectStartDate(customResultAcceptParams.getExpectStartDate())
                .expectEndDate(customResultAcceptParams.getExpectEndDate())
                .expectPrice(customResultAcceptParams.getExpectPrice())
                .message(customResultAcceptParams.getMessage())
                .build();
    }

    public static CustomResult ofRejection(Custom custom, CustomResultRejectParams customResultRejectParams) {
        return CustomResult.builder()
                .custom(custom)
                .message(customResultRejectParams.getMessage())
                .build();
    }
}
