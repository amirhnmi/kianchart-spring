package com.kianchart.kianchart.service;

import com.kianchart.kianchart.mapper.PermissionMapper;
import com.kianchart.kianchart.model.PermissionModel;
import com.kianchart.kianchart.enums.SortDirection;
import com.kianchart.kianchart.utils.exception.NotFoundException;
import com.kianchart.kianchart.entity.PermissionEntity;
import com.kianchart.kianchart.repository.PermissionRepository;
import com.kianchart.kianchart.validation.PermissionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {
    private final PermissionRepository repository;
    private final PermissionValidator validator;

    @Autowired
    public PermissionService(PermissionRepository repository, PermissionValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public List<PermissionModel.Response> getAllPermission(SortDirection sort, int skip, int limit) {
        List<PermissionEntity> permissionEntities = sort == SortDirection.asc ?
                repository.getAllPermissionASC() : repository.getAllPermissionDESC();
        return PermissionMapper.INSTANCE.entitiesToModel(permissionEntities);
    }

    public Long countAllPermissions() {
        return repository.countAllPermissions();
    }

    public PermissionModel.Response getOnePermission(Long id) {
        PermissionEntity permissionEntity = repository.getOnePermission(id);
        if (permissionEntity == null) {
            throw new NotFoundException("data not found with id " + id);
        }
        return PermissionMapper.INSTANCE.entityToModel(permissionEntity);
    }

    public PermissionModel.Response createPermission(PermissionModel.CreatePermissionRequest createRequest) {
        validator.createValidate(createRequest);
        PermissionEntity permissionEntity = PermissionMapper.INSTANCE.modelToEntity(createRequest);
        PermissionEntity savedPermissionEntity = repository.save(permissionEntity);
        return PermissionMapper.INSTANCE.entityToModel(savedPermissionEntity);
    }

    public PermissionModel.Response updatePermission(Long id, PermissionModel.UpdatePermissionRequest updateRequest) {
        PermissionEntity permissionEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("data not found with id " + id));
        validator.updateValidate(updateRequest);
        PermissionMapper.INSTANCE.modelToEntityForUpdate(updateRequest);
        PermissionEntity updatedPermissionEntity = repository.save(permissionEntity);
        return PermissionMapper.INSTANCE.entityToModel(updatedPermissionEntity);
    }

    public void deletePermission(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("data not found with id " + id);
        }
        repository.deletePermission(id);
    }
}
