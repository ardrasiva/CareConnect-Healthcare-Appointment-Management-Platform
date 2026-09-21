package com.careconnect.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentResponse {

    private Long id;

    private Long doctorId;

    private String doctorName;

    private LocalDate appointmentDate;

    private LocalTime appointmentTime;

    private String status;

    public AppointmentResponse(
            Long id,
            Long doctorId,
            String doctorName,
            LocalDate appointmentDate,
            LocalTime appointmentTime,
            String status) {

        this.id = id;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public String getStatus() {
        return status;
    }
}