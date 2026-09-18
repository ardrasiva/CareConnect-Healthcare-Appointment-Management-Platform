package com.careconnect.controller;

import com.careconnect.service.AppointmentService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(
            AppointmentService appointmentService) {

        this.appointmentService =
                appointmentService;
    }

    @GetMapping
    public String listAppointments(
            @RequestParam(required = false)
            String date,
            Model model) {

        if (date == null || date.isBlank()) {

            model.addAttribute(
                "appointments",
                appointmentService.getAppointmentsByDate(
                    LocalDate.now()
                )
            );

            model.addAttribute(
                "selectedDate",
                LocalDate.now()
            );

        } else {

            LocalDate selectedDate =
                    LocalDate.parse(date);

            model.addAttribute(
                "appointments",
                appointmentService
                    .getAppointmentsByDate(
                        selectedDate
                    )
            );

            model.addAttribute(
                "selectedDate",
                selectedDate
            );
        }

        return "portal/appointments/list";
    }
    @GetMapping("/view/{id}")
public String viewAppointment(
        @PathVariable Long id,
        Model model) {

    model.addAttribute(
        "appointment",
        appointmentService.getAppointmentById(id)
    );

    return "portal/appointments/view";
}
}