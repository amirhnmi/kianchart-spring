package com.kianchart.kianchart.api.permission.rolePermissions;

import com.kianchart.kianchart.api.permission.permissions.PermissionDTO;
import com.kianchart.kianchart.api.permission.roles.RoleDTO;
import com.kianchart.kianchart.core.exception.DuplicationException;
import com.kianchart.kianchart.core.exception.ValidationException;
import com.kianchart.kianchart.database.entity.permissions.Permission;
import com.kianchart.kianchart.database.entity.permissions.Role;
import com.kianchart.kianchart.database.entity.permissions.RolePermission;
import com.kianchart.kianchart.database.repository.permissions.RolePermissionRepository;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

public class RolePermissionDTO {

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
        private PermissionDTO.Response permission;
        private RoleDTO.Response role;

        public static Response mapToDto(RolePermission rolePermission) {
            Response dto = new Response();
            dto.setId(rolePermission.getId());
            dto.setRole(RoleDTO.Response.mapToDto(rolePermission.getRole()));
            dto.setPermission(PermissionDTO.Response.mapToDto(rolePermission.getPermission()));
            return dto;
        }

        public static List<Response> mapToDtoList(List<RolePermission> rolePermissions, int skip, int limit) {
            return rolePermissions.stream().skip(skip).limit(limit).map(Response::mapToDto).collect(Collectors.toList());
        }
    }

}
