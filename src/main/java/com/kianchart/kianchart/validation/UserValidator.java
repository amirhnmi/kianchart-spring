package com.kianchart.kianchart.validation;

import com.kianchart.kianchart.model.UserModel;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class UserValidator {
    Map<String, String> errors = new HashMap<>();

    public void createValidate(UserModel.Create userModel){
//        password validate
        if (userModel.getPassword() == null) {
            errors.put("password", "Password cannot be null.");
            return;
        }

        List<String> passwordErrors = new ArrayList<>();

        if (userModel.getPassword().length() <= 4) {
            passwordErrors.add("Password must have at least 4 characters");
        }
        if (!userModel.getPassword().matches(".*[a-z].*")) {
            passwordErrors.add("Password must include lowercase characters");
        }
        if (!userModel.getPassword().matches(".*[A-Z].*")) {
            passwordErrors.add("Password must include uppercase characters");
        }
        if (!userModel.getPassword().matches(".*\\d.*")) {
            passwordErrors.add("Password must contain numbers.");
        }

        if (!passwordErrors.isEmpty()) {
            errors.put("password", String.join(", ", passwordErrors));
        }

//        email validate
        if (userModel.getEmail() == null || userModel.getEmail().trim().isEmpty()) {
            errors.put("email", "Email can not be empty");
        } else if (!userModel.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            errors.put("email", "Email invalid");
        }

//        username validate
        if (userModel.getUsername() == null || userModel.getUsername().trim().isEmpty()) {
            errors.put("username", "Username must not be empty");
        } else if (userModel.getUsername().length() < 2 || userModel.getUsername().length() > 32) {
            errors.put("username", "Username must be between 2 and 32");
        }

//        gender validation
        List<String> validGender = Arrays.asList("male", "female", "not specific");
        if (userModel.getGender() == null || !validGender.contains(userModel.getGender().toLowerCase())) {
            errors.put("gender", "Gender must be one of: male, female, not specific.");
        }

//        dateOfBirth validate
        LocalDate minDate = LocalDate.of(1940, 1, 1);
        if (userModel.getBirthOfDate() == null || userModel.getBirthOfDate().isBefore(minDate)) {
            errors.put("dateOfBirth", "Date of birth must be after 1940-01-01.");
        }
    }


    public void updateValidate(UserModel.Update userModel){
//        password validate
        if (userModel.getPassword() == null) {
            errors.put("password", "Password cannot be null.");
            return;
        }

        List<String> passwordErrors = new ArrayList<>();

        if (userModel.getPassword().length() <= 4) {
            passwordErrors.add("Password must have at least 4 characters");
        }
        if (!userModel.getPassword().matches(".*[a-z].*")) {
            passwordErrors.add("Password must include lowercase characters");
        }
        if (!userModel.getPassword().matches(".*[A-Z].*")) {
            passwordErrors.add("Password must include uppercase characters");
        }
        if (!userModel.getPassword().matches(".*\\d.*")) {
            passwordErrors.add("Password must contain numbers.");
        }

        if (!passwordErrors.isEmpty()) {
            errors.put("password", String.join(", ", passwordErrors));
        }

//        email validate
        if (userModel.getEmail() == null || userModel.getEmail().trim().isEmpty()) {
            errors.put("email", "Email can not be empty");
        } else if (!userModel.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            errors.put("email", "Email invalid");
        }

//        username validate
        if (userModel.getUsername() == null || userModel.getUsername().trim().isEmpty()) {
            errors.put("username", "Username must not be empty");
        } else if (userModel.getUsername().length() < 2 || userModel.getUsername().length() > 32) {
            errors.put("username", "Username must be between 2 and 32");
        }

//        gender validation
        List<String> validGender = Arrays.asList("male", "female", "not specific");
        if (userModel.getGender() == null || !validGender.contains(userModel.getGender().toLowerCase())) {
            errors.put("gender", "Gender must be one of: male, female, not specific.");
        }

//        dateOfBirth validate
        LocalDate minDate = LocalDate.of(1940, 1, 1);
        if (userModel.getBirthOfDate() == null || userModel.getBirthOfDate().isBefore(minDate)) {
            errors.put("dateOfBirth", "Date of birth must be after 1940-01-01.");
        }
    }
}
