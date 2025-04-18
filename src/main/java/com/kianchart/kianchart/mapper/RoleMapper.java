package com.kianchart.kianchart.mapper;

import com.kianchart.kianchart.entity.RoleEntity;
import com.kianchart.kianchart.model.RoleModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleModel.Response entityToMode(RoleEntity roleEntity);

    List<RoleModel.Response> entitiesToModel(List<RoleEntity> roleEntities);

    RoleEntity modelToEntity(RoleModel.CreateRoleRequest createData);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void modelToEntityForUpdate(RoleModel.UpdateRoleRequest updateData,@MappingTarget RoleEntity existingRole);
}
