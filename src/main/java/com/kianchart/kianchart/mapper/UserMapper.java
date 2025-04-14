package com.kianchart.kianchart.mapper;

import com.kianchart.kianchart.model.UserModel;
import com.kianchart.kianchart.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;


@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,componentModel = "spring")
public interface UserMapper {
    UserModel.Response toUserModel(UserEntity userEntity);
    List<UserModel.Response> toUserModelList(List<UserEntity> userEntities);
    UserEntity toUserEntity(UserModel.Create userModel);
    UserEntity toUserEntityForUpdate(UserModel.Update userModel);

}
