
package com.careconnect.service;

import com.careconnect.dto.AppointmentRequest;
import com.careconnect.dto.AppointmentResponse;
import com.careconnect.entity.Appointment;
import com.careconnect.entity.AppointmentStatus;
import com.careconnect.entity.Doctor;
import com.careconnect.entity.User;
import com.careconnect.repository.AppointmentRepository;
import com.careconnect.repository.DoctorRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final DoctorRepository doctorRepository;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            DoctorRepository doctorRepository) {

        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
    }


    // ============================================================
    // EXISTING MANAGEMENT PORTAL FUNCTIONS
    // ============================================================

    // Get all appointments
    public List<Appointment> getAllAppointments() {

        return appointmentRepository.findAll();
    }


    // Get appointments by date
    public List<Appointment> getAppointmentsByDate(
            LocalDate date) {

        return appointmentRepository
                .findByAppointmentDate(date);
    }


    // Get appointment by ID
    public Appointment getAppointmentById(Long id) {

        return appointmentRepository
                .findById(id)
                .orElseThrow();
    }


    // Get appointments by patient ID
    public List<Appointment> getAppointmentsByPatient(
            Long patientId) {

        return appointmentRepository
                .findByPatientId(patientId);
    }


    // Get total appointment count
    public long getAppointmentCount() {

        return appointmentRepository.count();
    }


    // Get today's appointment count
    public long getTodaysAppointmentCount() {

        return appointmentRepository
                .countByAppointmentDate(LocalDate.now());
    }


    // ============================================================
    // PATIENT APPLICATION / REST API FUNCTIONS
    // ============================================================

    // GET /api/appointments
    // Get appointments belonging to the logged-in patient
    public List<AppointmentResponse> getPatientAppointments(
            User patient) {

        return appointmentRepository
                .findByPatientOrderByAppointmentDateAscAppointmentTimeAsc(
                        patient
                )
                .stream()
                .map(this::toResponse)
                .toList();
    }


    // POST /api/appointments
    // Book a new appointment
    @Transactional
    public AppointmentResponse createAppointment(
            AppointmentRequest request,
            User patient) {

        // Find the doctor
        Doctor doctor =
                doctorRepository
                        .findByIdAndIsActiveTrue(
                                request.getDoctorId()
                        )
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Doctor not found."
                                )
                        );


        // Check whether the selected time slot
        // is already booked
        boolean alreadyBooked =
                appointmentRepository
                        .existsByDoctorAndAppointmentDateAndAppointmentTimeAndStatus(
                                doctor,
                                request.getAppointmentDate(),
                                request.getAppointmentTime(),
                                AppointmentStatus.BOOKED
                        );


        if (alreadyBooked) {

            throw new IllegalArgumentException(
                    "The selected time slot is not available."
            );
        }


        // Create appointment
        Appointment appointment =
                new Appointment();


        // Set the logged-in patient
        appointment.setPatient(patient);


        // Set doctor
        appointment.setDoctor(doctor);


        // Set appointment date
        appointment.setAppointmentDate(
                request.getAppointmentDate()
        );


        // Set appointment time
        appointment.setAppointmentTime(
                request.getAppointmentTime()
        );


        // Set initial status
        appointment.setStatus(
                AppointmentStatus.BOOKED
        );


        // Save appointment
        Appointment saved =
                appointmentRepository.save(
                        appointment
                );


        // Convert entity to response DTO
        return toResponse(saved);
    }


    // DELETE /api/appointments/{id}
    // Cancel an appointment
    @Transactional
    public void cancelAppointment(
            Long id,
            User patient) {

        // Find appointment belonging to
        // the logged-in patient
        Appointment appointment =
                appointmentRepository
                        .findByIdAndPatient(
                                id,
                                patient
                        )
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Appointment not found."
                                )
                        );


        // Only booked appointments can be cancelled
        if (!"Booked".equalsIgnoreCase(
                appointment.getStatus().name())) {

            throw new IllegalArgumentException(
                    "This appointment cannot be cancelled."
            );
        }


        // Change status instead of deleting
        appointment.setStatus(
                AppointmentStatus.CANCELLED
        );


        // Save the updated appointment
        appointmentRepository.save(
                appointment
        );
    }


    // ============================================================
    // CONVERT APPOINTMENT ENTITY → RESPONSE DTO
    // ============================================================

    private AppointmentResponse toResponse(
            Appointment appointment) {

        return new AppointmentResponse(

                appointment.getId(),

                appointment.getDoctor().getId(),

                appointment.getDoctor().getName(),

                appointment.getAppointmentDate(),

                appointment.getAppointmentTime(),

                appointment.getStatus() != null
                        ? appointment.getStatus().name()
                        : null
        );
    }
}

