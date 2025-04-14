package com.kianchart.kianchart.service;

import com.kianchart.kianchart.model.PermissionModel;
import com.kianchart.kianchart.enums.SortDirection;
import com.kianchart.kianchart.utils.exception.NotFoundException;
import com.kianchart.kianchart.entity.PermissionEntity;
import com.kianchart.kianchart.repository.PermissionRepository;
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

    public List<PermissionModel.Response> getAllPermission(SortDirection sort, int skip, int limit) {
        List<PermissionEntity> permissionEntities = sort == SortDirection.asc ?
                permissionRepository.getAllPermissionASC() : permissionRepository.getAllPermissionDESC();
        return PermissionModel.Response.mapToDtoList(permissionEntities, skip, limit);
    }

    public Long countAllPermissions() {
        return permissionRepository.countAllPermissions();
    }

    public PermissionModel.Response getOnePermission(Long id) {
        PermissionEntity permissionEntity = permissionRepository.getOnePermission(id);
        if (permissionEntity == null) {
            throw new NotFoundException("data not found with id " + id);
        }
        return PermissionModel.Response.mapToDto(permissionEntity);
    }

    public PermissionModel.Response createPermission(PermissionModel.CreatePermissionRequest createRequest) {
        createRequest.validate(permissionRepository);
        PermissionEntity permissionEntity = createRequest.mapToEntity();
        PermissionEntity savedPermissionEntity = permissionRepository.save(permissionEntity);
        return PermissionModel.Response.mapToDto(savedPermissionEntity);
    }

    public PermissionModel.Response updatePermission(Long id, PermissionModel.UpdatePermissionRequest updateRequest) {
        PermissionEntity permissionEntity = permissionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("data not found with id " + id));
        updateRequest.validate(permissionRepository);
        updateRequest.mapToEntityForUpdate(permissionEntity);
        PermissionEntity updatedPermissionEntity = permissionRepository.save(permissionEntity);
        return PermissionModel.Response.mapToDto(updatedPermissionEntity);
    }

    public void deletePermission(Long id) {
        if (!permissionRepository.existsById(id)) {
            throw new NotFoundException("data not found with id " + id);
        }
        permissionRepository.deletePermission(id);
    }
}
