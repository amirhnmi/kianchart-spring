package com.kianchart.kianchart.model;

import com.kianchart.kianchart.mapper.PermissionMapper;
import com.kianchart.kianchart.mapper.RoleMapper;
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

    }

    @Getter
    @Setter
    public static class Response {
        private Long id;
        private PermissionModel.Response permission;
        private RoleModel.Response role;
    }

}
