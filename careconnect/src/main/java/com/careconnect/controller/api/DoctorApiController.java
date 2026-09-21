package com.careconnect.controller.api;

import com.careconnect.dto.DoctorResponse;
import com.careconnect.entity.Doctor;
import com.careconnect.service.DoctorService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorApiController {

    private final DoctorService doctorService;

    public DoctorApiController(
            DoctorService doctorService) {

        this.doctorService =
                doctorService;
    }

    @GetMapping
    public ResponseEntity<List<DoctorResponse>>
            getDoctors() {

        List<DoctorResponse> doctors =
                doctorService
                    .getActiveDoctors()
                    .stream()
                    .map(this::toResponse)
                    .toList();

        return ResponseEntity.ok(doctors);
    }

    private DoctorResponse toResponse(
            Doctor doctor) {

        return new DoctorResponse(
            doctor.getId(),
            doctor.getName(),
            doctor.getSpecialization(),
            doctor.getQualification(),
            doctor.getExperience(),
            doctor.getConsultationFee()
        );
    }
    @GetMapping("/{id}")
public ResponseEntity<DoctorResponse>
        getDoctor(
            @PathVariable Long id) {

    return doctorService
            .getActiveDoctorById(id)
            .map(this::toResponse)
            .map(ResponseEntity::ok)
            .orElseGet(
                () -> ResponseEntity.notFound().build()
            );
}
}