package com.kianchart.kianchart.api.permission.roles;

import com.kianchart.kianchart.core.enums.SortDirection;
import com.kianchart.kianchart.core.exception.NotFoundException;
import com.kianchart.kianchart.database.entity.permissions.Role;
import com.kianchart.kianchart.database.repository.permissions.RoleRepository;
import jakarta.validation.Valid;
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

    public List<RoleDTO.Response> getAllRole(SortDirection sort, int skip, int limit) {
        List<Role> roles = sort == SortDirection.asc ? roleRepository.getAllRoleASC() : roleRepository.getAllRoleDESC();
        return RoleDTO.Response.mapToDtoList(roles, skip, limit);
    }

    public Long countAllRole(){
        return roleRepository.countAllRole();
    }

    public RoleDTO.Response getOneRole(Long id) {
        Role role = roleRepository.getOneRole(id);
        if (role == null) {
            throw new NotFoundException("data not found with id " + id);
        }
        return RoleDTO.Response.mapToDto(role);
    }

    public RoleDTO.Response createRole(RoleDTO.CreateRoleRequest roleRequest) {
        roleRequest.validate(roleRepository);
        Role role = roleRequest.mapToEntity();
        Role savedRole = roleRepository.save(role);
        return RoleDTO.Response.mapToDto(savedRole);
    }

    public RoleDTO.Response updateRole(Long id, RoleDTO.UpdateRoleRequest updateRoleRequest) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("data not found with id " + id));
        updateRoleRequest.validate(roleRepository);
        updateRoleRequest.MapToEntityForUpdate(role);
        Role updatedRole = roleRepository.save(role);
        return RoleDTO.Response.mapToDto(updatedRole);
    }

    public void deleteRole(Long id) {
        if (!roleRepository.existingById(id)) {
            throw new NotFoundException("data not found with id " + id);
        }
        roleRepository.deleteRole(id);
    }
}
