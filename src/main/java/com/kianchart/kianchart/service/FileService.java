package com.kianchart.kianchart.service;

import com.kianchart.kianchart.mapper.FileMapper;
import com.kianchart.kianchart.model.FileModel;
import com.kianchart.kianchart.enums.FileType;
import com.kianchart.kianchart.enums.SortDirection;
import com.kianchart.kianchart.utils.exception.NotFoundException;
import com.kianchart.kianchart.utils.UploadFile;
import com.kianchart.kianchart.entity.FileEntity;
import com.kianchart.kianchart.repository.FileRepository;
import com.kianchart.kianchart.validation.FileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

;

@Service
public class FileService {
    private final FileRepository repository;
    private final FileValidator validator;
    private final FileMapper mapper;

    @Autowired
    public FileService(FileRepository fileRepository, FileValidator validator, FileMapper mapper) {
        this.repository = fileRepository;
        this.validator = validator;

        this.mapper = mapper;
    }

    public List<FileModel.Response> getAllFile(SortDirection sort, int skip, int limit, FileType fileType) {
        List<FileEntity> files = sort == SortDirection.asc ?
                repository.getAllFileASC(fileType) : repository.getAllImageDESC(fileType);
        return mapper.entitiesToModel(files);
    }

    public Long countAllFile(FileType fileType) {
        return repository.countAllFile(fileType);
    }

    public FileModel.Response createFile(MultipartFile file, Long creatorId, FileType fileType) throws IOException {
        FileModel.CreateFileRequest fileRequest = new FileModel.CreateFileRequest();
        fileRequest.setCreatorId(creatorId);
        fileRequest.setContent(file.getBytes());
        fileRequest.setType(fileType);

        FileEntity fileEntity = mapper.modelToEntity(fileRequest);

        if (fileType == FileType.image) {
            validator.imageValidation(file);
            String fileNameSaved = UploadFile.upload("uploads/images", file);
            fileEntity.setFile(fileNameSaved);
        } else if (fileType == FileType.video) {
            validator.videoValidation(file);
            String videoNameSaved = UploadFile.upload("uploads/videos", file);
            fileEntity.setFile(videoNameSaved);
        } else if (fileType == FileType.document) {
            validator.documentValidation(file);
            String documentNameSaved = UploadFile.upload("uploads/documents", file);
            fileEntity.setFile(documentNameSaved);
        }
        FileEntity savedFile = repository.save(fileEntity);
        return mapper.entityToModel(savedFile);
    }

    public void deleteFile(Long id) {
        if (!repository.existingById(id)) {
            throw new NotFoundException("data not found with id " + id);
        }
        repository.deleteFile(id);
    }
}
