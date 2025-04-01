package com.kianchart.kianchart.api.permission.permissions;

import com.kianchart.kianchart.core.enums.SortDirection;
import com.kianchart.kianchart.core.exception.NotFoundException;
import com.kianchart.kianchart.database.entity.permissions.Permission;
import com.kianchart.kianchart.database.repository.permissions.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {
    private final PermissionRepository permissionRepository;

    @Autowired
    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public List<PermissionDTO.Response> getAllPermission(SortDirection sort, int skip, int limit) {
        List<Permission> permissions = sort == SortDirection.asc ?
                permissionRepository.getAllPermissionASC() : permissionRepository.getAllPermissionDESC();
        return PermissionDTO.Response.mapToDtoList(permissions, skip, limit);
    }

    public Long countAllPermissions() {
        return permissionRepository.countAllPermissions();
    }

    public PermissionDTO.Response getOnePermission(Long id) {
        Permission permission = permissionRepository.getOnePermission(id);
        if (permission == null) {
            throw new NotFoundException("data not found with id " + id);
        }
        return PermissionDTO.Response.mapToDto(permission);
    }

    public PermissionDTO.Response createPermission(PermissionDTO.CreatePermissionRequest createRequest) {
        createRequest.validate(permissionRepository);
        Permission permission = createRequest.mapToEntity();
        Permission savedPermission = permissionRepository.save(permission);
        return PermissionDTO.Response.mapToDto(savedPermission);
    }

    public PermissionDTO.Response updatePermission(Long id, PermissionDTO.UpdatePermissionRequest updateRequest) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("data not found with id " + id));
        updateRequest.validate(permissionRepository);
        updateRequest.mapToEntityForUpdate(permission);
        Permission updatedPermission = permissionRepository.save(permission);
        return PermissionDTO.Response.mapToDto(updatedPermission);
    }

    public void deletePermission(Long id) {
        if (!permissionRepository.existsById(id)) {
            throw new NotFoundException("data not found with id " + id);
        }
        permissionRepository.deletePermission(id);
    }
}
