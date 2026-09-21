package com.careconnect.repository;

import com.careconnect.dto.DoctorReport;
import com.careconnect.entity.Doctor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("""
        SELECT new com.careconnect.dto.DoctorReport(
            d,
            COUNT(a)
        )
        FROM Doctor d
        LEFT JOIN Appointment a
            ON a.doctor.id = d.id
        GROUP BY d
        ORDER BY COUNT(a) DESC
    """)
    List<DoctorReport> getPopularDoctors();
    List<Doctor> findByNameContainingIgnoreCaseOrSpecializationContainingIgnoreCase(
        String name,
        String specialization
);
List<Doctor> findByIsActiveTrue();

Optional<Doctor> findByIdAndIsActiveTrue(
        Long id
);
}