package com.careconnect.service;

import com.careconnect.dto.DoctorReport;
import com.careconnect.entity.Doctor;
import com.careconnect.repository.DoctorRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(
            DoctorRepository doctorRepository) {

        this.doctorRepository = doctorRepository;
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

        doctorRepository.deleteById(id);
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
