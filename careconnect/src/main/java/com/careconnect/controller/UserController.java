package com.careconnect.controller;

import com.careconnect.entity.User;
import com.careconnect.service.AppointmentService;
import com.careconnect.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final AppointmentService appointmentService;

    public UserController(
            UserService userService,
            AppointmentService appointmentService) {

        this.userService = userService;
        this.appointmentService =
                appointmentService;
    }


    @GetMapping
    public String listUsers(
            @RequestParam(required = false)
            String search,
            Model model) {

        List<User> users =
                userService.getAllPatients();


        // If search text is entered
        if (search != null &&
            !search.isBlank()) {

            String keyword =
                    search.trim().toLowerCase();


            users = users.stream()
                    .filter(user ->

                        (
                            user.getFirstName()
                                + " "
                                + user.getLastName()
                        )
                        .toLowerCase()
                        .contains(keyword)

                        ||

                        user.getEmail()
                                .toLowerCase()
                                .contains(keyword)

                        ||

                        user.getPhoneNumber()
                                .toLowerCase()
                                .contains(keyword)

                    )
                    .toList();
        }


        model.addAttribute(
            "users",
            users
        );


        model.addAttribute(
            "search",
            search
        );

model.addAttribute(
    "activePage",
    "users"
);
        return "portal/users/list";
    }


    @GetMapping("/{id}")
    public String viewUser(
            @PathVariable Long id,
            Model model) {

        User user =
                userService.getUserById(id);


        model.addAttribute(
            "user",
            user
        );


        model.addAttribute(
            "appointments",
            appointmentService
                .getAppointmentsByPatient(id)
        );
model.addAttribute(
    "activePage",
    "users"
);

        return "portal/users/view";
    }
}