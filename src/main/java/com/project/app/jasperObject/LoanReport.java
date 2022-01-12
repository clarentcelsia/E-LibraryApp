package com.project.app.jasperObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LoanReport {
    private String dateBorrow;
    private String dateReturn;
    private String bookCode;
    private String userName;
    private String bookName;
}
