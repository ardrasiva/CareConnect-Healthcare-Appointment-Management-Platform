package com.careconnect.controller.api;

import com.careconnect.dto.AppointmentRequest;
import com.careconnect.dto.AppointmentResponse;
import com.careconnect.entity.User;
import com.careconnect.service.AppointmentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentApiController {

    private final AppointmentService
            appointmentService;

    public AppointmentApiController(
            AppointmentService appointmentService) {

        this.appointmentService =
                appointmentService;
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponse>>
            getAppointments(
                Authentication authentication) {

        User patient =
                (User) authentication
                    .getPrincipal();

        List<AppointmentResponse> appointments =
                appointmentService
                    .getPatientAppointments(patient);

        return ResponseEntity.ok(
            appointments
        );
    }

    @PostMapping
    public ResponseEntity<?> createAppointment(
            @RequestBody
            AppointmentRequest request,
            Authentication authentication) {

        try {

            User patient =
                    (User) authentication
                        .getPrincipal();

            AppointmentResponse response =
                    appointmentService
                        .createAppointment(
                            request,
                            patient
                        );

            return ResponseEntity
                    .status(
                        HttpStatus.CREATED
                    )
                    .body(response);

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .badRequest()
                    .body(
                        Map.of(
                            "error",
                            e.getMessage()
                        )
                    );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelAppointment(
            @PathVariable Long id,
            Authentication authentication) {

        try {

            User patient =
                    (User) authentication
                        .getPrincipal();

            appointmentService
                .cancelAppointment(
                    id,
                    patient
                );

            return ResponseEntity.ok(
                Map.of(
                    "message",
                    "Appointment cancelled."
                )
            );

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .badRequest()
                    .body(
                        Map.of(
                            "error",
                            e.getMessage()
                        )
                    );
        }
    }
}