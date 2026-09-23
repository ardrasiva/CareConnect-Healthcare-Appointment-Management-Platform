package com.careconnect.service;

import com.careconnect.dto.DoctorReport;
import com.careconnect.entity.Doctor;
import com.careconnect.repository.AppointmentRepository;
import com.careconnect.repository.DoctorRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;

    public DoctorService(
        DoctorRepository doctorRepository,
        AppointmentRepository appointmentRepository) {

    this.doctorRepository = doctorRepository;
    this.appointmentRepository =
            appointmentRepository;
}

    public List<Doctor> getAllDoctors() {

        return doctorRepository.findAll();
    }

    public Optional<Doctor> getDoctorById(Long id) {

        return doctorRepository.findById(id);
    }

    public Doctor saveDoctor(Doctor doctor) {

        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id) {

    Doctor doctor =
            doctorRepository
                    .findById(id)
                    .orElseThrow(() ->
                        new IllegalArgumentException(
                            "Doctor not found."
                        )
                    );


    boolean hasAppointments =
            appointmentRepository
                    .existsByDoctor(doctor);


    if (hasAppointments) {

        throw new IllegalArgumentException(
            "Cannot delete this doctor because they have appointments."
        );
    }


    doctorRepository.delete(doctor);
}
    public List<DoctorReport> getPopularDoctors() {

    return doctorRepository
            .getPopularDoctors();
}
public List<Doctor> searchDoctors(String search) {

    return doctorRepository
        .findByNameContainingIgnoreCaseOrSpecializationContainingIgnoreCase(
            search,
            search
        );
}
//DoctorService.java

public long getDoctorCount() {
    return doctorRepository.count();
}
public List<Doctor> getActiveDoctors() {

    return doctorRepository
            .findByIsActiveTrue();
}
public Optional<Doctor> getActiveDoctorById(
        Long id) {

    return doctorRepository
            .findByIdAndIsActiveTrue(id);
}
}
