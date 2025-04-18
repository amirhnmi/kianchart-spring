package com.kianchart.kianchart.validation;

import com.kianchart.kianchart.model.PermissionModel;
import com.kianchart.kianchart.repository.PermissionRepository;
import com.kianchart.kianchart.utils.exception.DuplicationException;
import org.springframework.stereotype.Component;

@Component
public class PermissionValidator {
    private final PermissionRepository repository;

    public PermissionValidator(PermissionRepository repository) {
        this.repository = repository;
    }

    public void createValidate(PermissionModel.CreatePermissionRequest createData){
        if (repository.existsBySlug(createData.getSlug())){
            throw new DuplicationException("slug already exist");
        }
    }

    public void updateValidate(PermissionModel.UpdatePermissionRequest updateData){
        if (updateData.getSlug() != null && repository.existsBySlug(updateData.getSlug())){
            throw new DuplicationException("slug already exist");
        }
    }
}
