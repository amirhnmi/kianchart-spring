package com.kianchart.kianchart.model;

import com.kianchart.kianchart.entity.RoleEntity;
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

        public UserRoleEntity mapToEntity(UserRoleRepository userRoleRepository) {
            RoleEntity roleEntity = userRoleRepository.getOneRole(this.roleId);
            UserEntity userEntity = userRoleRepository.getOneUser(this.userId);

            UserRoleEntity userRoleEntity = new UserRoleEntity();
            userRoleEntity.setUser(userEntity);
            userRoleEntity.setRole(roleEntity);
            return userRoleEntity;
        }
    }

    @Getter
    @Setter
    public static class Response {
        private Long id;
        private UserModel.Response user;
        private RoleModel.Response role;

        public static UserRoleModel.Response mapToDto(UserRoleEntity userRoleEntity) {
            UserRoleModel.Response dto = new UserRoleModel.Response();
            dto.setId(userRoleEntity.getId());
            dto.setRole(RoleModel.Response.mapToDto(userRoleEntity.getRole()));
//            dto.setUser(UserModel.Response.mapToDto(userRoleEntity.getUserEntity()));
            return dto;
        }

        public static List<UserRoleModel.Response> mapToDtoList(List<UserRoleEntity> userRoleEntities, int skip, int limit) {
            return userRoleEntities.stream().skip(skip).limit(limit).map(UserRoleModel.Response::mapToDto).collect(Collectors.toList());
        }
    }
}
