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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RolePermissionDTO {

    @Getter
    @Setter
    public static class CreateRolePermissionRequest{
        @NotNull(message = "roleId is required")
        private Long roleId;

        @NotNull(message = "permissionId is required")
        private Long permissionId;

        public void validate(RolePermissionRepository rolePermissionRepository){
            Map<String,String> errors = new HashMap<>();
            if (!rolePermissionRepository.existsByRoleId(this.roleId)){
                errors.put("roleId", "roleId not found with id "+ this.roleId);
            }
            if (!rolePermissionRepository.existsByPermissionId(this.permissionId)){
                errors.put("permissionId", "permissionId not found with id "+ this.permissionId);
            }
            if (rolePermissionRepository.existsByPermissionIdRoleId(this.permissionId,this.roleId)){
                throw new DuplicationException(
                        "roleId with id "+ this.roleId + " and permissionId with id "+ this.permissionId + " already exist"
                );
            }

            if (!errors.isEmpty()){
                throw new ValidationException(errors);
            }
        }

        public RolePermission mapToEntity(RolePermissionRepository rolePermissionRepository){
            Role role = rolePermissionRepository.getOneRole(this.roleId);
            Permission permission = rolePermissionRepository.getOnePermission(this.permissionId);

            RolePermission rolePermission = new RolePermission();
            rolePermission.setPermission(permission);
            rolePermission.setRole(role);
            return rolePermission;
        }
    }

    @Getter
    @Setter
    public static class Response {
        private Long id;
        private PermissionDTO.Response permission;
        private RoleDTO.Response role;

        public static RolePermissionDTO.Response mapToDto(RolePermission rolePermission) {
            RolePermissionDTO.Response dto = new RolePermissionDTO.Response();
            dto.setId(rolePermission.getId());
            dto.setRole(RoleDTO.Response.mapToDto(rolePermission.getRole()));
            dto.setPermission(PermissionDTO.Response.mapToDto(rolePermission.getPermission()));
            return dto;
        }

        public static List<RolePermissionDTO.Response> mapToDtoList(List<RolePermission> rolePermissions, int skip, int limit) {
            return rolePermissions.stream().skip(skip).limit(limit).map(RolePermissionDTO.Response::mapToDto).collect(Collectors.toList());
        }
    }
}
