package com.kianchart.kianchart.core.validation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Validation {

    public static void password(String password, Map<String, String> errors) {
        if (password == null) {
            errors.put("password", "Password cannot be null.");
            return;
        }

        List<String> passwordErrors = new ArrayList<>();

        if (password.length() <= 4) {
            passwordErrors.add("Password must have at least 4 characters");
        }
        if (!password.matches(".*[a-z].*")) {
            passwordErrors.add("Password must include lowercase characters");
        }
        if (!password.matches(".*[A-Z].*")) {
            passwordErrors.add("Password must include uppercase characters");
        }
        if (!password.matches(".*\\d.*")) {
            passwordErrors.add("Password must contain numbers.");
        }

        if (!passwordErrors.isEmpty()) {
            errors.put("password", String.join(", ", passwordErrors));
        }
    }

    public static void email(String email, Map<String, String> errors) {
        if (email == null || email.trim().isEmpty()) {
            errors.put("email", "Email can not be empty");
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            errors.put("email", "Email invalid");
        }
    }

    public static void username(String username, Map<String, String> errors) {
        if (username == null || username.trim().isEmpty()) {
            errors.put("username", "Username must not be empty");
        } else if (username.length() < 2 || username.length() > 32) {
            errors.put("username", "Username must be between 2 and 32");
        }
    }

    public static void gender(String gender, Map<String, String> errors) {
        List<String> validGender = Arrays.asList("male", "female", "not specific");
        if (gender == null || !validGender.contains(gender.toLowerCase())) {
            errors.put("gender", "Gender must be one of: male, female, not specific.");
        }
    }

    public static void dateOfBirth(LocalDate dateOfBirth, Map<String, String> errors) {
        LocalDate minDate = LocalDate.of(1940, 1, 1);
        if (dateOfBirth == null || dateOfBirth.isBefore(minDate)) {
            errors.put("dateOfBirth", "Date of birth must be after 1940-01-01.");
        }
    }
}
