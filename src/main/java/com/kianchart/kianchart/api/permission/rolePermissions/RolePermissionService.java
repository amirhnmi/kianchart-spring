package com.kianchart.kianchart.api.permission.rolePermissions;

import com.kianchart.kianchart.core.enums.SortDirection;
import com.kianchart.kianchart.core.exception.NotFoundException;
import com.kianchart.kianchart.database.entity.permissions.RolePermission;
import com.kianchart.kianchart.database.repository.permissions.RolePermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolePermissionService {
    private final RolePermissionRepository rolePermissionRepository;

    @Autowired
    public RolePermissionService(RolePermissionRepository rolePermissionRepository){
        this.rolePermissionRepository = rolePermissionRepository;
    }

    public List<RolePermissionDTO.Response> getAllRolePermission(SortDirection sort, int skip, int limit){
        List<RolePermission> rolePermissions = sort == SortDirection.asc ?
                rolePermissionRepository.getAllRolePermissionASC() : rolePermissionRepository.getAllRolePermissionDESC();
        return RolePermissionDTO.Response.mapToDtoList(rolePermissions,skip,limit);
    }

    public Long countAllRolePermission(){
        return rolePermissionRepository.count();
    }

    public RolePermissionDTO.Response createRolePermission(RolePermissionDTO.CreateRolePermissionRequest createRequest){
        createRequest.validate(rolePermissionRepository);
        RolePermission rolePermission = createRequest.mapToEntity(rolePermissionRepository);
        RolePermission savedRolePermission = rolePermissionRepository.save(rolePermission);
        return RolePermissionDTO.Response.mapToDto(savedRolePermission);
    }

    public void deleteRolePermission(Long id){
        if (!rolePermissionRepository.existsById(id)){
            throw new NotFoundException("data not found with id " + id);
        }
        rolePermissionRepository.deleteById(id);
    }
}
