package com.kianchart.kianchart.service;

import com.kianchart.kianchart.model.RolePermissionModel;
import com.kianchart.kianchart.entity.PermissionEntity;
import com.kianchart.kianchart.entity.RoleEntity;
import com.kianchart.kianchart.enums.SortDirection;
import com.kianchart.kianchart.utils.exception.NotFoundException;
import com.kianchart.kianchart.entity.RolePermissionEntity;
import com.kianchart.kianchart.repository.RolePermissionRepository;
import jakarta.transaction.Transactional;
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

    public List<RolePermissionModel.Response> getAllRolePermission(SortDirection sort, int skip, int limit){
        List<RolePermissionEntity> rolePermissionEntities = sort == SortDirection.asc ?
                rolePermissionRepository.getAllRolePermissionASC() : rolePermissionRepository.getAllRolePermissionDESC();
        return RolePermissionModel.Response.mapToDtoList(rolePermissionEntities,skip,limit);
    }

    public Long countAllRolePermission(){
        return rolePermissionRepository.count();
    }

    @Transactional
    public Map<String, String> createRolePermission(RolePermissionModel.CreateRolePermissionRequest createRequest){
        List<RolePermissionEntity> data = new ArrayList<>();
        Map<String, String> response = new HashMap<>();
        createRequest.validate(rolePermissionRepository);

        Set<Long> currentPermissionIds = rolePermissionRepository.getAllPermissionInRolePermission(createRequest.getRoleId());
        Set<Long> newPermissionIds = new HashSet<>(createRequest.getPermissionIds());

        Set<Long> needAddPermissionIds = new HashSet<>(newPermissionIds);
        needAddPermissionIds.removeAll(currentPermissionIds);

        Set<Long> needRemovePermissionIds = new HashSet<>(currentPermissionIds);
        needRemovePermissionIds.removeAll(newPermissionIds);

        RoleEntity roleEntity = rolePermissionRepository.getOneRole(createRequest.getRoleId());

        if (!needRemovePermissionIds.isEmpty()){
            rolePermissionRepository.deleteByRoleIdAndPermissionIds(createRequest.getRoleId(),needRemovePermissionIds);
        }

        for (Long permissionId : needAddPermissionIds) {
            PermissionEntity permissionEntity = rolePermissionRepository.getOnePermission(permissionId);

            RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
            rolePermissionEntity.setPermission(permissionEntity);
            rolePermissionEntity.setRole(roleEntity);
            data.add(rolePermissionEntity);
        }

        rolePermissionRepository.saveAll(data);

        response.put("message","RoleEntity permissions changed successfully");
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
