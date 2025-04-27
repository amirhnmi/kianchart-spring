package com.kianchart.kianchart.mapper;

import com.kianchart.kianchart.entity.FileEntity;
import com.kianchart.kianchart.model.FileModel;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface FileMapper {
    FileMapper INSTANCE = Mappers.getMapper(FileMapper.class);

    FileEntity modelToEntity(FileModel.CreateFileRequest request);

    FileModel.Response entityToModel(FileEntity entity);

    List<FileModel.Response> entitiesToModel(List<FileEntity> files);
}

//@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL, componentModel = "spring")
//public abstract class FileMapper {
//    @Autowired
//    private UserRepository repository;
//
//    public abstract FileModel.Response entityToModel(FileEntity fileData);
//
//    public abstract List<FileModel.Response> entitiesToModel(List<FileEntity> fileEntities);
//
//    @Mapping(source = "creatorId", target = "creator", qualifiedByName = "mapCreatorIdToUser")
//    public abstract FileEntity modelToEntity(FileModel.CreateFileRequest createData);
//
//    @Named("mapCreatorIdToUser")
//    protected UserEntity mapCreatorIdToUser(Long creatorId){
//        return repository.findById(creatorId)
//                .orElseThrow(() -> new NotFoundException("User not found with id: " + creatorId));
//    }
//}
