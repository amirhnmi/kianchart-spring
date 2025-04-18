package com.kianchart.kianchart.mapper;

import com.kianchart.kianchart.entity.PermissionEntity;
import com.kianchart.kianchart.model.PermissionModel;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface PermissionMapper {
    PermissionMapper INSTANCE = Mappers.getMapper(PermissionMapper.class);

    PermissionModel.Response entityToModel(PermissionEntity permissionEntity);

    List<PermissionModel.Response> entitiesToModel(List<PermissionEntity> permissionEntities);

    PermissionEntity modelToEntity(PermissionModel.CreatePermissionRequest PermissionModel);

    PermissionEntity modelToEntityForUpdate(PermissionModel.UpdatePermissionRequest permissionModel);
}
