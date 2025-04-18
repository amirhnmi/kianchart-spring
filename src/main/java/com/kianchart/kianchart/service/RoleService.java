package com.kianchart.kianchart.service;

import com.kianchart.kianchart.mapper.RoleMapper;
import com.kianchart.kianchart.model.RoleModel;
import com.kianchart.kianchart.entity.RoleEntity;
import com.kianchart.kianchart.enums.SortDirection;
import com.kianchart.kianchart.utils.exception.NotFoundException;
import com.kianchart.kianchart.repository.RoleRepository;
import com.kianchart.kianchart.validation.RoleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository repository;
    private final RoleValidator validator;

    @Autowired
    public RoleService(RoleRepository roleRepository, RoleValidator validator) {
        this.repository = roleRepository;
        this.validator = validator;
    }

    public List<RoleModel.Response> getAllRole(SortDirection sort, int skip, int limit) {
        List<RoleEntity> roleEntities = sort == SortDirection.asc ? repository.getAllRoleASC() : repository.getAllRoleDESC();
        return RoleMapper.INSTANCE.entitiesToModel(roleEntities);
    }

    public Long countAllRole(){
        return repository.countAllRole();
    }

    public RoleModel.Response getOneRole(Long id) {
        RoleEntity roleEntity = repository.getOneRole(id);
        if (roleEntity == null) {
            throw new NotFoundException("data not found with id " + id);
        }
        return RoleMapper.INSTANCE.entityToMode(roleEntity);
    }

    public RoleModel.Response createRole(RoleModel.CreateRoleRequest roleRequest) {
        validator.createValidate(roleRequest);
        RoleEntity roleEntity = RoleMapper.INSTANCE.modelToEntity(roleRequest);
        RoleEntity savedRoleEntity = repository.save(roleEntity);
        return RoleMapper.INSTANCE.entityToMode(savedRoleEntity);
    }

    public RoleModel.Response updateRole(Long id, RoleModel.UpdateRoleRequest updateRoleRequest) {
        RoleEntity roleEntity = repository.findById(id).orElseThrow(() -> new NotFoundException("data not found with id " + id));
        validator.updateValidate(updateRoleRequest);
        RoleMapper.INSTANCE.modelToEntityForUpdate(updateRoleRequest, roleEntity);
        RoleEntity updatedRoleEntity = repository.save(roleEntity);
        return RoleMapper.INSTANCE.entityToMode(updatedRoleEntity);
    }

    public void deleteRole(Long id) {
        if (!repository.existingById(id)) {
            throw new NotFoundException("data not found with id " + id);
        }
        repository.deleteRole(id);
    }
}
