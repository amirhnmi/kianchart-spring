package com.kianchart.kianchart.mapper;

import com.kianchart.kianchart.entity.FileEntity;
import com.kianchart.kianchart.entity.UserEntity;
import com.kianchart.kianchart.entity.UserRoleEntity;
import com.kianchart.kianchart.model.FileModel;
import com.kianchart.kianchart.model.UserRoleModel;
import com.kianchart.kianchart.repository.UserRepository;
import com.kianchart.kianchart.utils.exception.NotFoundException;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL, componentModel = "spring")
public abstract class FileMapper {
    @Autowired
    private UserRepository repository;

    public abstract FileModel.Response entityToModel(FileEntity fileData);

    public abstract List<FileModel.Response> entitiesToModel(List<FileEntity> fileEntities);

    @Mapping(source = "creatorId", target = "creator", qualifiedByName = "mapCreatorIdToUser")
    public abstract FileEntity modelToEntity(FileModel.CreateFileRequest createData);

    @Named("mapCreatorIdToUser")
    protected UserEntity mapCreatorIdToUser(Long creatorId){
        return repository.findById(creatorId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + creatorId));
    }
}
