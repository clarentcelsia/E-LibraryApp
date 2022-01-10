package com.project.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoanDTO {
    private String searchByStatus;
    private String searchByDateBorrow;
}
