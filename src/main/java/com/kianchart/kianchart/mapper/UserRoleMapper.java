package com.kianchart.kianchart.mapper;

import com.kianchart.kianchart.entity.UserRoleEntity;
import com.kianchart.kianchart.model.UserRoleModel;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, componentModel = "spring")
public interface UserRoleMapper {
    UserRoleMapper INSTANCE = Mappers.getMapper(UserRoleMapper.class);

    UserRoleModel.Response entityToModel(UserRoleEntity userRoleEntity);

    List<UserRoleModel.Response> entitiesToModel(List<UserRoleEntity> userRoleEntities);

    UserRoleEntity modelToEntity(UserRoleModel.CreateUserRoleRequest createData);
}
