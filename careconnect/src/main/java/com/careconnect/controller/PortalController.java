package com.careconnect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.careconnect.service.AppointmentService;
import com.careconnect.service.DoctorService;
import com.careconnect.service.UserService;

@Controller
public class PortalController {
    
    private final DoctorService doctorService;
    
    private final AppointmentService appointmentService;
    
    private final UserService userService;

    public  PortalController(DoctorService doctorService, AppointmentService appointmentService, UserService userService){
        this.appointmentService=appointmentService;
        this.doctorService=doctorService;
        this.userService=userService;
    }

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("doctor_count", doctorService.getDoctorCount());
        model.addAttribute("patient_count", userService.getPatientCount());
        model.addAttribute("todays_appointment_count", appointmentService.getTodaysAppointmentCount());
        model.addAttribute("todays_appointment_count", appointmentService.getTodaysAppointmentCount());
        model.addAttribute("appointment_count", appointmentService.getAppointmentCount());
        model.addAttribute("activePage", "dashboard");

        return "portal/home";
    }

    @GetMapping("/login")
    public String login() {

        return "portal/login";
    }
}