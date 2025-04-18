package com.kianchart.kianchart.validation;

import com.kianchart.kianchart.model.PermissionModel;
import com.kianchart.kianchart.model.RoleModel;
import com.kianchart.kianchart.repository.PermissionRepository;
import com.kianchart.kianchart.repository.RoleRepository;
import com.kianchart.kianchart.utils.exception.DuplicationException;
import org.springframework.stereotype.Component;

@Component
public class RoleValidator {
    private final RoleRepository repository;

    public RoleValidator(RoleRepository repository) {
        this.repository = repository;
    }

    public void createValidate(RoleModel.CreateRoleRequest createData){
        if (repository.existsBySlug(createData.getSlug())) {
            throw new DuplicationException("slug already exists");
        }
    }

    public void updateValidate(RoleModel.UpdateRoleRequest updateData){
        if (updateData.getSlug() != null && repository.existsBySlug(updateData.getSlug())) {
            throw new DuplicationException("slug already exists");
        }
    }
}
