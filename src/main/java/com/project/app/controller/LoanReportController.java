package com.project.app.controller;

import com.project.app.jasperObject.LoanReport;
import com.project.app.service.impl.LoanReportService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LoanReportController {
    @Autowired
    LoanReportService service;

    @GetMapping("/loan-pdf")
    public ResponseEntity<byte []> generateLoanPdf() throws FileNotFoundException, JRException {
        List<LoanReport> loanReport = service.createReport();

        // get file design of jasper report
        File file = ResourceUtils.getFile("classpath:loan.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        // datasource to fill the report
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(loanReport);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Testing params");

        // fill report with design and data source
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // export to pdf.
        byte[] pdfReport = JasperExportManager.exportReportToPdf(jasperPrint);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=loan.pdf");

        return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.APPLICATION_PDF).body(pdfReport);
    }
}

