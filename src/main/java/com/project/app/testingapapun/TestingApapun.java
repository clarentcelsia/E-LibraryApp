package com.project.app.testingapapun;

import javax.xml.crypto.Data;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TestingApapun {
    public static void main(String[] args) {
        LocalDateTime localDate = LocalDateTime.now();

        LocalDateTime returnDate = LocalDateTime.of(LocalDate.of(2022,1,6), LocalTime.now());
        Duration duration = Duration.between(localDate, returnDate);


        String str = "2022-01-06";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(str, formatter);
        LocalDateTime dateParsed = LocalDateTime.of(dateTime, LocalTime.now());
        System.out.println(dateParsed);

        System.out.println(localDate);
        System.out.println(returnDate);

        System.out.println(duration.toDays());

    }
}
