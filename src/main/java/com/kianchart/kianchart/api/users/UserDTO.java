package com.kianchart.kianchart.api.users;

import com.kianchart.kianchart.core.exception.DuplicationException;
import com.kianchart.kianchart.core.exception.ValidationException;
import com.kianchart.kianchart.core.validation.Validation;
import com.kianchart.kianchart.database.entity.User;
import com.kianchart.kianchart.database.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class UserDTO {

    @Getter
    @Setter
    public static class CreateRequest {

        private String username;
        private String email;
        private String password;
        private String fullname;
        private LocalDate birthOfDate;
        private String gender;
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
        public User toEntity() {
            User user = new User();
            user.setUsername(this.username);
            user.setEmail(this.email);
            user.setPassword(this.password);
            user.setFullname(this.fullname);
            user.setDateOfBirth(this.birthOfDate);
            user.setGender(this.gender);
            user.setIsActive(this.isActive);
            return user;
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

        //      mapping Entity to DTO
        public static Response toDto(User user) {
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
    }
}
