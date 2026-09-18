package com.careconnect.controller;

import com.careconnect.entity.Doctor;
import com.careconnect.service.DoctorService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(
            DoctorService doctorService) {

        this.doctorService = doctorService;
    }

    @GetMapping
    public String listDoctors(Model model) {

        model.addAttribute(
            "doctors",
            doctorService.getAllDoctors()
        );

        return "portal/doctors/list";
    }
    @GetMapping("/add")
public String addDoctorForm(Model model) {

    model.addAttribute(
        "doctor",
        new Doctor()
    );

    return "portal/doctors/form";
}
@PostMapping("/add")
public String addDoctor(
        @Valid @ModelAttribute("doctor") Doctor doctor,
        BindingResult result) {

    if (result.hasErrors()) {

        return "portal/doctors/form";
    }

    doctorService.saveDoctor(doctor);

    return "redirect:/doctors";
}
@GetMapping("/edit/{id}")
public String editDoctorForm(
        @PathVariable Long id,
        Model model) {

    Doctor doctor = doctorService
            .getDoctorById(id)
            .orElseThrow();

    model.addAttribute(
        "doctor",
        doctor
    );

    return "portal/doctors/form";
}
@PostMapping("/edit/{id}")
public String editDoctor(
        @PathVariable Long id,
        @Valid @ModelAttribute("doctor") Doctor doctor,
        BindingResult result) {

    if (result.hasErrors()) {

        return "portal/doctors/form";
    }

    Doctor existingDoctor = doctorService
            .getDoctorById(id)
            .orElseThrow();

    existingDoctor.setName(doctor.getName());
    existingDoctor.setSpecialization(
        doctor.getSpecialization()
    );
    existingDoctor.setQualification(
        doctor.getQualification()
    );
    existingDoctor.setExperience(
        doctor.getExperience()
    );
    existingDoctor.setPhoneNumber(
        doctor.getPhoneNumber()
    );
    existingDoctor.setEmail(
        doctor.getEmail()
    );
    existingDoctor.setConsultationFee(
        doctor.getConsultationFee()
    );
    existingDoctor.setIsActive(
        doctor.getIsActive()
    );

    doctorService.saveDoctor(existingDoctor);

    return "redirect:/doctors";
}
@GetMapping("/delete/{id}")
public String deleteDoctor(
        @PathVariable Long id) {

    doctorService.deleteDoctor(id);

    return "redirect:/doctors";
}
}