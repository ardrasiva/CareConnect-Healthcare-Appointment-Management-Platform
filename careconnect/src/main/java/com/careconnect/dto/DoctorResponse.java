package com.careconnect.dto;

import java.math.BigDecimal;

public class DoctorResponse {

    private Long id;

    private String name;

    private String specialization;

    private String qualification;

    private Integer experience;

    private BigDecimal consultationFee;

    public DoctorResponse(
            Long id,
            String name,
            String specialization,
            String qualification,
            Integer experience,
            BigDecimal consultationFee) {

        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.qualification = qualification;
        this.experience = experience;
        this.consultationFee = consultationFee;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getQualification() {
        return qualification;
    }

    public Integer getExperience() {
        return experience;
    }

    public BigDecimal getConsultationFee() {
        return consultationFee;
    }
}