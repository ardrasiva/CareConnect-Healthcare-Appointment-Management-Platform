package com.careconnect.controller;

import com.careconnect.service.DoctorService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reports")
public class ReportController {

    private final DoctorService doctorService;

    public ReportController(
            DoctorService doctorService) {

        this.doctorService = doctorService;
    }

    @GetMapping
    public String popularDoctors(Model model) {

        model.addAttribute(
            "doctors",
            doctorService.getPopularDoctors()
        );
         model.addAttribute(
        "activePage",
        "reports"
    );
        return "portal/reports/popular_doctors";
    }
}