package com.kianchart.kianchart.model;

import com.kianchart.kianchart.entity.UserRoleEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class UserModel {

    @Getter
    @Setter
    public static class Create extends UserModel {

        @NotBlank(message = "username is required")
        @Size(min = 2, max = 128, message = "username must be between 2 and 128")
        private String username;

        @NotBlank(message = "email is required")
        private String email;

        @NotBlank(message = "password is required")
        private String password;

        @NotBlank(message = "fullname is required")
        @Size(min = 2, max = 128, message = "fullname must be between 2 and 128")
        private String fullname;

        @NotNull(message = "dateOfBirth is required")
        private LocalDate dateOfBirth;

        @NotBlank(message = "gender is required")
        private String gender;

        @NotNull(message = "isActive is required")
        private Boolean isActive;
    }

    @Getter
    @Setter
    public static class Update extends UserModel {

        @Size(min = 2, max = 128, message = "username must be between 2 and 128")
        private String username;
        private String email;
        private String password;

        @Size(min = 2, max = 128, message = "fullname must be between 2 and 128")
        private String fullname;

        private LocalDate birthOfDate;
        private String gender;
        private Boolean isActive;
    }


    @Getter
    @Setter
    public static class Response extends UserModel {
        private Long id;
        private String email;
        private String username;
        private String fullname;
        private LocalDate birthOfDate;
        private String gender;
        private Boolean isActive;
        private UserRoleEntity userRole;
    }
}
