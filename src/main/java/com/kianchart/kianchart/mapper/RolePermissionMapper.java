package com.kianchart.kianchart.mapper;

import com.kianchart.kianchart.entity.RolePermissionEntity;
import com.kianchart.kianchart.entity.UserRoleEntity;
import com.kianchart.kianchart.model.RolePermissionModel;
import com.kianchart.kianchart.model.UserRoleModel;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RolePermissionMapper {
    RolePermissionMapper INSTANCE = Mappers.getMapper(RolePermissionMapper.class);

    RolePermissionModel.Response entityToModel(RolePermissionEntity rolePermissionEntity);

    List<RolePermissionModel.Response> entitiesToModel(List<RolePermissionEntity> rolePermissionEntities);

    RolePermissionEntity modelToEntity(RolePermissionModel.CreateRolePermissionRequest createData);
}
