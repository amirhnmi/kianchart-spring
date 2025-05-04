package com.kianchart.kianchart.mapper;

import com.kianchart.kianchart.entity.UserEntity;
import com.kianchart.kianchart.model.UserModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

        UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
//    static UserMapper INSTANCE() {
//        return Mappers.getMapper(UserMapper.class);
//    }

    UserModel.Response toUserModel(UserEntity userEntity);

    List<UserModel.Response> toUserModelList(List<UserEntity> userEntities);

    UserEntity toUserEntity(UserModel.Create userModel);

    void toUserEntityForUpdate(UserModel.Update userModel, @MappingTarget UserEntity userEntity);

}
