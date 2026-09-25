package com.careconnect.service;

import com.careconnect.entity.Appointment;
import com.careconnect.entity.Doctor;
import com.careconnect.entity.User;
import com.careconnect.repository.AppointmentRepository;
import com.careconnect.repository.DoctorRepository;
import com.careconnect.repository.UserRepository;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

@Service
public class PdfReportService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;

    public PdfReportService(
            DoctorRepository doctorRepository,
            UserRepository userRepository,
            AppointmentRepository appointmentRepository) {

        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public byte[] generateOverviewPdf() {

        try {

            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();

            Document document =
                    new Document(PageSize.A4, 36, 36, 40, 40);

            PdfWriter.getInstance(
                    document,
                    outputStream
            );

            document.open();


            // =========================
            // TITLE
            // =========================

            Font titleFont =
                    new Font(
                            Font.HELVETICA,
                            24,
                            Font.BOLD,
                            new Color(18, 59, 120)
                    );

            Paragraph title =
                    new Paragraph(
                            "CareConnect",
                            titleFont
                    );

            title.setAlignment(
                    Element.ALIGN_CENTER
            );

            document.add(title);


            Font subtitleFont =
                    new Font(
                            Font.HELVETICA,
                            14,
                            Font.NORMAL,
                            Color.DARK_GRAY
                    );

            Paragraph subtitle =
                    new Paragraph(
                            "Hospital Overview Report",
                            subtitleFont
                    );

            subtitle.setAlignment(
                    Element.ALIGN_CENTER
            );

            document.add(subtitle);


            Paragraph date =
                    new Paragraph(
                            "Date: " + LocalDate.now()
                    );

            date.setAlignment(
                    Element.ALIGN_CENTER
            );

            document.add(date);

            document.add(
                    new Paragraph(" ")
            );


            // =========================
            // GET DATA
            // =========================

            List<Doctor> doctors =
                    doctorRepository.findAll();

            List<User> users =
                    userRepository.findAll();

            List<Appointment> appointments =
                    appointmentRepository.findAll();


            long doctorCount =
                    doctors.size();

            long patientCount =
                    users.stream()
                            .filter(user ->
                                    user.getRole() != null
                                            &&
                                    !user.getRole()
                                            .toString()
                                            .equalsIgnoreCase("ADMIN")
                            )
                            .count();

            long appointmentCount =
                    appointments.size();

            long todaysAppointmentCount =
                    appointments.stream()
                            .filter(appointment ->
                                    LocalDate.now().equals(
                                            appointment
                                                    .getAppointmentDate()
                                    )
                            )
                            .count();


            // =========================
            // OVERVIEW
            // =========================

            Font sectionFont =
                    new Font(
                            Font.HELVETICA,
                            16,
                            Font.BOLD,
                            new Color(18, 59, 120)
                    );

            document.add(
                    new Paragraph(
                            "Overview",
                            sectionFont
                    )
            );

            document.add(
                    new Paragraph(" ")
            );


            PdfPTable overviewTable =
                    new PdfPTable(2);

            overviewTable.setWidthPercentage(100);

            addHeader(
                    overviewTable,
                    "Details"
            );

            addHeader(
                    overviewTable,
                    "Count"
            );


            addRow(
                    overviewTable,
                    "Total Doctors",
                    String.valueOf(doctorCount)
            );

            addRow(
                    overviewTable,
                    "Total Patients",
                    String.valueOf(patientCount)
            );

            addRow(
                    overviewTable,
                    "Today's Appointments",
                    String.valueOf(
                            todaysAppointmentCount
                    )
            );

            addRow(
                    overviewTable,
                    "Total Bookings",
                    String.valueOf(
                            appointmentCount
                    )
            );


            document.add(
                    overviewTable
            );


            // =========================
            // TODAY'S APPOINTMENTS
            // =========================

            document.add(
                    new Paragraph(" ")
            );

            document.add(
                    new Paragraph(
                            "Today's Appointments",
                            sectionFont
                    )
            );

            document.add(
                    new Paragraph(" ")
            );


            List<Appointment> todaysAppointments =
                    appointments.stream()
                            .filter(appointment ->
                                    LocalDate.now().equals(
                                            appointment
                                                    .getAppointmentDate()
                                    )
                            )
                            .toList();


            if (todaysAppointments.isEmpty()) {

                document.add(
                        new Paragraph(
                                "No appointments available today."
                        )
                );

            } else {

                PdfPTable appointmentTable =
                        new PdfPTable(4);

                appointmentTable.setWidthPercentage(
                        100
                );


                addHeader(
                        appointmentTable,
                        "Time"
                );

                addHeader(
                        appointmentTable,
                        "Patient"
                );

                addHeader(
                        appointmentTable,
                        "Doctor"
                );

                addHeader(
                        appointmentTable,
                        "Status"
                );


                for (
                        Appointment appointment :
                        todaysAppointments
                ) {

                    User patient =
                            appointment.getPatient();

                    Doctor doctor =
                            appointment.getDoctor();


                    String patientName =
                            patient.getFirstName()
                                    + " "
                                    + patient.getLastName();


                    appointmentTable.addCell(
                            String.valueOf(
                                    appointment
                                            .getAppointmentTime()
                            )
                    );

                    appointmentTable.addCell(
                            patientName
                    );

                    appointmentTable.addCell(
                            doctor.getName()
                    );

                    appointmentTable.addCell(
                            appointment.getStatus()
                                    != null
                                    ? appointment
                                        .getStatus()
                                        .name()
                                    : ""
                    );
                }


                document.add(
                        appointmentTable
                );
            }


            // =========================
            // DOCTOR DETAILS
            // =========================

            document.newPage();


            document.add(
                    new Paragraph(
                            "Doctor Details",
                            sectionFont
                    )
            );

            document.add(
                    new Paragraph(" ")
            );


            PdfPTable doctorTable =
                    new PdfPTable(5);

            doctorTable.setWidthPercentage(
                    100
            );


            addHeader(
                    doctorTable,
                    "Name"
            );

            addHeader(
                    doctorTable,
                    "Specialization"
            );

            addHeader(
                    doctorTable,
                    "Qualification"
            );

            addHeader(
                    doctorTable,
                    "Experience"
            );

            addHeader(
                    doctorTable,
                    "Status"
            );


            for (Doctor doctor : doctors) {

                doctorTable.addCell(
                        doctor.getName()
                );

                doctorTable.addCell(
                        doctor.getSpecialization()
                );

                doctorTable.addCell(
                        doctor.getQualification()
                );

                doctorTable.addCell(
                        String.valueOf(
                                doctor.getExperience()
                        )
                );

                doctorTable.addCell(
                        Boolean.TRUE.equals(
                                doctor.getIsActive()
                        )
                                ? "Active"
                                : "Inactive"
                );
            }


            document.add(
                    doctorTable
            );


            // =========================
            // FOOTER
            // =========================

            document.add(
                    new Paragraph(" ")
            );

            Paragraph footer =
                    new Paragraph(
                            "CareConnect Hospital Management System"
                    );

            footer.setAlignment(
                    Element.ALIGN_CENTER
            );

            document.add(footer);


            document.close();

            return outputStream.toByteArray();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to generate PDF.",
                    e
            );
        }
    }


    // =========================
    // HELPER METHODS
    // =========================

    private void addHeader(
            PdfPTable table,
            String text) {

        Font font =
                new Font(
                        Font.HELVETICA,
                        10,
                        Font.BOLD,
                        Color.WHITE
                );

        PdfPCell cell =
                new PdfPCell(
                        new Phrase(
                                text,
                                font
                        )
                );

        cell.setBackgroundColor(
                new Color(18, 59, 120)
        );

        cell.setPadding(7);

        cell.setHorizontalAlignment(
                Element.ALIGN_CENTER
        );

        table.addCell(cell);
    }


    private void addRow(
            PdfPTable table,
            String label,
            String value) {

        PdfPCell labelCell =
                new PdfPCell(
                        new Phrase(label)
                );

        labelCell.setPadding(7);


        PdfPCell valueCell =
                new PdfPCell(
                        new Phrase(value)
                );

        valueCell.setPadding(7);

        valueCell.setHorizontalAlignment(
                Element.ALIGN_CENTER
        );


        table.addCell(labelCell);
        table.addCell(valueCell);
    }
}