package com.careconnect.controller;

import com.careconnect.service.PdfReportService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reports/pdf")
public class PdfReportController {

    private final PdfReportService pdfReportService;

    public PdfReportController(
            PdfReportService pdfReportService) {

        this.pdfReportService = pdfReportService;
    }

    @GetMapping("/overview")
    public ResponseEntity<byte[]> generateOverviewPdf() {

        byte[] pdf =
                pdfReportService.generateOverviewPdf();

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=CareConnect_Overview.pdf"
                )
                .contentType(
                        MediaType.APPLICATION_PDF
                )
                .body(pdf);
    }
}