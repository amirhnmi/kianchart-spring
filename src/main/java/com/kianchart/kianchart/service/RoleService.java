package com.kianchart.kianchart.service;

import com.kianchart.kianchart.model.RoleModel;
import com.kianchart.kianchart.entity.RoleEntity;
import com.kianchart.kianchart.enums.SortDirection;
import com.kianchart.kianchart.utils.exception.NotFoundException;
import com.kianchart.kianchart.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleModel.Response> getAllRole(SortDirection sort, int skip, int limit) {
        List<RoleEntity> roleEntities = sort == SortDirection.asc ? roleRepository.getAllRoleASC() : roleRepository.getAllRoleDESC();
        return RoleModel.Response.mapToDtoList(roleEntities, skip, limit);
    }

    public Long countAllRole(){
        return roleRepository.countAllRole();
    }

    public RoleModel.Response getOneRole(Long id) {
        RoleEntity roleEntity = roleRepository.getOneRole(id);
        if (roleEntity == null) {
            throw new NotFoundException("data not found with id " + id);
        }
        return RoleModel.Response.mapToDto(roleEntity);
    }

    public RoleModel.Response createRole(RoleModel.CreateRoleRequest roleRequest) {
        roleRequest.validate(roleRepository);
        RoleEntity roleEntity = roleRequest.mapToEntity();
        RoleEntity savedRoleEntity = roleRepository.save(roleEntity);
        return RoleModel.Response.mapToDto(savedRoleEntity);
    }

    public RoleModel.Response updateRole(Long id, RoleModel.UpdateRoleRequest updateRoleRequest) {
        RoleEntity roleEntity = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("data not found with id " + id));
        updateRoleRequest.validate(roleRepository);
        updateRoleRequest.MapToEntityForUpdate(roleEntity);
        RoleEntity updatedRoleEntity = roleRepository.save(roleEntity);
        return RoleModel.Response.mapToDto(updatedRoleEntity);
    }

    public void deleteRole(Long id) {
        if (!roleRepository.existingById(id)) {
            throw new NotFoundException("data not found with id " + id);
        }
        roleRepository.deleteRole(id);
    }
}
