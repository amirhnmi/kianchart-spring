package com.kianchart.kianchart.model;

import com.kianchart.kianchart.entity.RoleEntity;
import com.kianchart.kianchart.mapper.RoleMapper;
import com.kianchart.kianchart.utils.exception.DuplicationException;
import com.kianchart.kianchart.utils.exception.ValidationException;
import com.kianchart.kianchart.entity.UserEntity;
import com.kianchart.kianchart.entity.UserRoleEntity;
import com.kianchart.kianchart.repository.UserRoleRepository;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserRoleModel {
    @Getter
    @Setter
    public static class CreateUserRoleRequest {
        @NotNull(message = "userId is required")
        private Long userId;

        @NotNull(message = "roleId is required")
        private Long roleId;

        public void validate(UserRoleRepository userRoleRepository) {

        }

    }

    @Getter
    @Setter
    public static class Response {
        private Long id;
        private UserModel.Response user;
        private RoleModel.Response role;
    }
}
