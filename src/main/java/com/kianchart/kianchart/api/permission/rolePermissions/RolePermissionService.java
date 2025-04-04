package com.kianchart.kianchart.api.permission.rolePermissions;

import com.kianchart.kianchart.core.enums.SortDirection;
import com.kianchart.kianchart.core.exception.NotFoundException;
import com.kianchart.kianchart.database.entity.permissions.Permission;
import com.kianchart.kianchart.database.entity.permissions.Role;
import com.kianchart.kianchart.database.entity.permissions.RolePermission;
import com.kianchart.kianchart.database.repository.permissions.RolePermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public Map<String, String> createRolePermission(RolePermissionDTO.CreateRolePermissionRequest createRequest){
        List<RolePermission> data = new ArrayList<>();
        Map<String, String> response = new HashMap<>();
        createRequest.validate(rolePermissionRepository);

        Set<Long> currentPermissionIds = rolePermissionRepository.getAllPermissionInRolePermission(createRequest.getRoleId());
        Set<Long> newPermissionIds = new HashSet<>(createRequest.getPermissionIds());

        Set<Long> needAddPermissionIds = new HashSet<>(newPermissionIds);
        needAddPermissionIds.removeAll(currentPermissionIds);

        Set<Long> needRemovePermissionIds = new HashSet<>(currentPermissionIds);
        needRemovePermissionIds.removeAll(newPermissionIds);

        Role role = rolePermissionRepository.getOneRole(createRequest.getRoleId());

        if (!needRemovePermissionIds.isEmpty()){
            rolePermissionRepository.deleteByRoleIdAndPermissionIds(createRequest.getRoleId(),needRemovePermissionIds);
        }

        for (Long permissionId : needAddPermissionIds) {
            Permission permission = rolePermissionRepository.getOnePermission(permissionId);

            RolePermission rolePermission = new RolePermission();
            rolePermission.setPermission(permission);
            rolePermission.setRole(role);
            data.add(rolePermission);
        }

        rolePermissionRepository.saveAll(data);

        response.put("message","Role permissions changed successfully");
        response.put("added_permissions", String.join(",", needAddPermissionIds.stream().map(String::valueOf).toList()));
        response.put("deleted_permissions", String.join(",", needRemovePermissionIds.stream().map(String::valueOf).toList()));
        return response;
    }

    public void deleteRolePermission(Long id){
        if (!rolePermissionRepository.existsById(id)){
            throw new NotFoundException("data not found with id " + id);
        }
        rolePermissionRepository.deleteById(id);
    }
}
