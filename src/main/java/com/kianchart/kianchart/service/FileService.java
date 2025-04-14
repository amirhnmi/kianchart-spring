package com.kianchart.kianchart.service;

import com.kianchart.kianchart.model.FileModel;
import com.kianchart.kianchart.enums.FileType;
import com.kianchart.kianchart.enums.SortDirection;
import com.kianchart.kianchart.utils.exception.NotFoundException;
import com.kianchart.kianchart.utils.UploadFile;
import com.kianchart.kianchart.entity.FileEntity;
import com.kianchart.kianchart.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

;

@Service
public class FileService {
    private final FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public List<FileModel.Response> getAllFile(SortDirection sort, int skip, int limit, FileType fileType) {
        List<FileEntity> files = sort == SortDirection.asc ?
                fileRepository.getAllFileASC(fileType) : fileRepository.getAllImageDESC(fileType);
        return FileModel.Response.mapToDtoList(files, skip, limit);
    }

    public Long countAllFile(FileType fileType) {
        return fileRepository.countAllFile(fileType);
    }

    public FileModel.Response createFile(MultipartFile file, Long creatorId, FileType fileType) throws IOException {
        FileEntity fileEntity = FileModel.CreateFileRequest.mapToEntity(fileRepository, file, creatorId, fileType);
        if (fileType == FileType.image) {
            FileModel.CreateFileRequest.imageValidation(file);
            String fileNameSaved = UploadFile.upload("uploads/images", file);
            fileEntity.setFile(fileNameSaved);
        } else if (fileType == FileType.video) {
            FileModel.CreateFileRequest.videoValidation(file);
            String videoNameSaved = UploadFile.upload("uploads/videos", file);
            fileEntity.setFile(videoNameSaved);
        } else if (fileType == FileType.document) {
            FileModel.CreateFileRequest.documentValidation(file);
            String documentNameSaved = UploadFile.upload("uploads/documents", file);
            fileEntity.setFile(documentNameSaved);
        }
        FileEntity savedfile = fileRepository.save(fileEntity);
        return FileModel.Response.mapToDto(savedfile);
    }

    public void deleteFile(Long id) {
        if (!fileRepository.existingById(id)) {
            throw new NotFoundException("data not found with id " + id);
        }
        fileRepository.deleteFile(id);
    }
}
