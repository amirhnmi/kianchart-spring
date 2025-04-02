package com.kianchart.kianchart.api.users.userRoles;

import com.kianchart.kianchart.api.permission.roles.RoleDTO;
import com.kianchart.kianchart.api.users.users.UserDTO;
import com.kianchart.kianchart.core.exception.DuplicationException;
import com.kianchart.kianchart.core.exception.ValidationException;
import com.kianchart.kianchart.database.entity.permissions.Role;
import com.kianchart.kianchart.database.entity.users.User;
import com.kianchart.kianchart.database.entity.users.UserRole;
import com.kianchart.kianchart.database.repository.users.UserRoleRepository;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserRoleDTO {
    @Getter
    @Setter
    public static class CreateUserRoleRequest {
        @NotNull(message = "userId is required")
        private Long userId;

        @NotNull(message = "roleId is required")
        private Long roleId;

        public void validate(UserRoleRepository userRoleRepository) {
            Map<String, String> errors = new HashMap<>();

            if (!userRoleRepository.existsByRoleId(this.roleId)) {
                errors.put("roleId", "roleId not found with id " + this.roleId);
            }
            if (!userRoleRepository.existsByUserId(this.userId)) {
                errors.put("userId", "userId not found with id " + this.userId);
            }
            if (userRoleRepository.existsByUserIdRoleId(this.userId, this.roleId)) {
                throw new DuplicationException(
                        "roleId with id " + this.roleId + " and userId with id " + this.userId + " already exist"
                );
            }

            if (!errors.isEmpty()) {
                throw new ValidationException(errors);
            }
        }

        public UserRole mapToEntity(UserRoleRepository userRoleRepository) {
            Role role = userRoleRepository.getOneRole(this.roleId);
            User user = userRoleRepository.getOneUser(this.userId);

            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRole(role);
            return userRole;
        }
    }

    @Getter
    @Setter
    public static class Response {
        private Long id;
        private UserDTO.Response user;
        private RoleDTO.Response role;

        public static UserRoleDTO.Response mapToDto(UserRole userRole) {
            UserRoleDTO.Response dto = new UserRoleDTO.Response();
            dto.setId(userRole.getId());
            dto.setRole(RoleDTO.Response.mapToDto(userRole.getRole()));
            dto.setUser(UserDTO.Response.mapToDto(userRole.getUser()));
            return dto;
        }

        public static List<UserRoleDTO.Response> mapToDtoList(List<UserRole> userRoles, int skip, int limit) {
            return userRoles.stream().skip(skip).limit(limit).map(UserRoleDTO.Response::mapToDto).collect(Collectors.toList());
        }
    }
}
