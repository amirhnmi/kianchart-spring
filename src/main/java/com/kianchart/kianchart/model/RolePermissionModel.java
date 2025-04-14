package com.kianchart.kianchart.model;

import com.kianchart.kianchart.utils.exception.ValidationException;
import com.kianchart.kianchart.entity.RolePermissionEntity;
import com.kianchart.kianchart.repository.RolePermissionRepository;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

public class RolePermissionModel {

    @Getter
    @Setter
    public static class CreateRolePermissionRequest {
        @NotNull(message = "roleId is required")
        private Long roleId;

        @NotNull(message = "permissionId is required")
        private List<Long> permissionIds;

        public void validate(RolePermissionRepository rolePermissionRepository) {
            Map<String, String> errors = new HashMap<>();
            if (!rolePermissionRepository.existsByRoleId(this.roleId)) {
                errors.put("roleId", "roleId not found with id " + this.roleId);
            }
            for (Long permissionId : permissionIds) {
                if (!rolePermissionRepository.existsByPermissionId(permissionId)) {
                    errors.put("permissionId", "permissionId not found with id " + permissionId);
                }
//                if (rolePermissionRepository.existsByPermissionIdRoleId(permissionId, this.roleId)) {
//                    throw new DuplicationException(
//                            "roleId with id " + this.roleId + " and permissionId with id " + permissionId + " already exist"
//                    );
//                }
            }
            if (!errors.isEmpty()) {
                throw new ValidationException(errors);
            }
        }

        private void mapToEntity(RolePermissionRepository rolePermissionRepository) {}
    }

    @Getter
    @Setter
    public static class Response {
        private Long id;
        private PermissionModel.Response permission;
        private RoleModel.Response role;

        public static Response mapToDto(RolePermissionEntity rolePermissionEntity) {
            Response dto = new Response();
            dto.setId(rolePermissionEntity.getId());
            dto.setRole(RoleModel.Response.mapToDto(rolePermissionEntity.getRole()));
            dto.setPermission(PermissionModel.Response.mapToDto(rolePermissionEntity.getPermission()));
            return dto;
        }

        public static List<Response> mapToDtoList(List<RolePermissionEntity> rolePermissionEntities, int skip, int limit) {
            return rolePermissionEntities.stream().skip(skip).limit(limit).map(Response::mapToDto).collect(Collectors.toList());
        }
    }

}
