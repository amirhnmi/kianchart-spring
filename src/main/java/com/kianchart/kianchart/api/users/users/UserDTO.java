package com.kianchart.kianchart.api.users.users;

import com.kianchart.kianchart.core.exception.DuplicationException;
import com.kianchart.kianchart.core.exception.ValidationException;
import com.kianchart.kianchart.core.utils.BcryptHashPassword;
import com.kianchart.kianchart.core.validation.Validation;
import com.kianchart.kianchart.database.entity.users.User;
import com.kianchart.kianchart.database.entity.users.UserRole;
import com.kianchart.kianchart.database.repository.users.UserRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserDTO {

    @Getter
    @Setter
    public static class CreateRequest {

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

        @NotNull(message = "birthOfDate is required")
        private LocalDate birthOfDate;

        @NotBlank(message = "gender is required")
        private String gender;

        @NotNull(message = "isActive is required")
        private Boolean isActive;

        public void validate(UserRepository userRepository) {
            Map<String, String> errors = new HashMap<>();

            if (userRepository.existsByEmail(this.email)) {
                throw new DuplicationException("Email already exist.");
            }
            if (userRepository.existsByUsername(this.username)) {
                throw new DuplicationException("Username already exist.");
            }

            Validation.username(this.username, errors);
            Validation.email(this.email, errors);
            Validation.password(this.password, errors);
            Validation.gender(this.gender, errors);
            Validation.dateOfBirth(this.birthOfDate, errors);

            if (!errors.isEmpty()) {
                throw new ValidationException(errors);
            }
        }

        // mapping DTO to Entity
        public User mapToEntity() {
            User user = new User();
            user.setUsername(this.username);
            user.setEmail(this.email);
            user.setPassword(BcryptHashPassword.hash(this.password));
            user.setFullname(this.fullname);
            user.setDateOfBirth(this.birthOfDate);
            user.setGender(this.gender);
            user.setIsActive(this.isActive);
            return user;
        }
    }

    @Getter @Setter
    public static class UpdateRequest {

        @Size(min = 2, max = 128, message = "username must be between 2 and 128")
        private String username;
        private String email;
        private String password;

        @Size(min = 2, max = 128, message = "fullname must be between 2 and 128")
        private String fullname;

        private LocalDate birthOfDate;
        private String gender;
        private Boolean isActive;

        public void validate(UserRepository userRepository) {
            Map<String, String> errors = new HashMap<>();

            if (this.email != null && userRepository.existsByEmail(this.email)) {
                throw new DuplicationException("Email already exist.");
            }
            if (this.username != null && userRepository.existsByUsername(this.username)) {
                throw new DuplicationException("Username already exist.");
            }

            if (this.username != null) Validation.username(this.username, errors);
            if (this.email != null) Validation.email(this.email, errors);
            if (this.password != null) Validation.password(this.password, errors);
            if (this.gender != null) Validation.gender(this.gender, errors);
            if (this.birthOfDate != null) Validation.dateOfBirth(this.birthOfDate, errors);

            if (!errors.isEmpty()) {
                throw new ValidationException(errors);
            }
        }

        // mapping DTO to Entity
        public void mapToEntityForUpdate(User user) {
            if (this.username != null) user.setUsername(this.username);
            if (this.email != null) user.setEmail(this.email);
            if (this.password != null) user.setPassword(BcryptHashPassword.hash(this.password));
            if (this.fullname != null) user.setFullname(this.fullname);
            if (this.birthOfDate != null) user.setDateOfBirth(this.birthOfDate);
            if (this.gender != null) user.setGender(this.gender);
            if (this.isActive != null) user.setIsActive(this.isActive);
        }
    }


    @Getter
    @Setter
    public static class Response {
        private Long id;
        private String email;
        private String username;
        private String fullname;
        private LocalDate birthOfDate;
        private String gender;
        private Boolean isActive;
        private UserRole userRole;

        //      mapping Entity to DTO
        public static Response mapToDto(User user) {
            Response dto = new Response();
            dto.setId(user.getId());
            dto.setEmail(user.getEmail());
            dto.setUsername(user.getUsername());
            dto.setFullname(user.getFullname());
            dto.setBirthOfDate(user.getDateOfBirth());
            dto.setGender(user.getGender());
            dto.setIsActive(user.getIsActive());
            return dto;
        }

        public static List<Response> mapToDtoList(List<User> users, int skip, int limit) {
            return users.stream().skip(skip).limit(limit).map(Response::mapToDto).collect(Collectors.toList());
        }
    }
}
