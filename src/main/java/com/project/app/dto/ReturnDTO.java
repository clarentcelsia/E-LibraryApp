package com.project.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ReturnDTO {
    private Integer searchByTotalQty;
    private Integer searchByTotalPenaltyFee;
    private String searchByDateReturn;
}
