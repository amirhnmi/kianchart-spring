package com.kianchart.kianchart.api.general.files;

import com.kianchart.kianchart.core.enums.FileType;
import com.kianchart.kianchart.core.enums.SortDirection;
import com.kianchart.kianchart.core.exception.NotFoundException;
import com.kianchart.kianchart.core.utils.UploadFile;
import com.kianchart.kianchart.database.entity.general.File;
import com.kianchart.kianchart.database.repository.general.FileRepository;
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

    public List<FileDTO.Response> getAllFile(SortDirection sort, int skip, int limit, FileType fileType) {
        List<File> files = sort == SortDirection.asc ?
                fileRepository.getAllFileASC(fileType) : fileRepository.getAllImageDESC(fileType);
        return FileDTO.Response.mapToDtoList(files, skip, limit);
    }

    public Long countAllFile(FileType fileType) {
        return fileRepository.countAllFile(fileType);
    }

    public FileDTO.Response createFile(MultipartFile file, Long creatorId, FileType fileType) throws IOException {
        File fileEntity = FileDTO.CreateFileRequest.mapToEntity(fileRepository, file, creatorId, fileType);
        if (fileType == FileType.image) {
            FileDTO.CreateFileRequest.imageValidation(file);
            String fileNameSaved = UploadFile.upload("uploads/images", file);
            fileEntity.setFile(fileNameSaved);
        } else if (fileType == FileType.video) {
            FileDTO.CreateFileRequest.videoValidation(file);
            String videoNameSaved = UploadFile.upload("uploads/videos", file);
            fileEntity.setFile(videoNameSaved);
        } else if (fileType == FileType.document) {
            FileDTO.CreateFileRequest.documentValidation(file);
            String documentNameSaved = UploadFile.upload("uploads/documents", file);
            fileEntity.setFile(documentNameSaved);
        }
        File savedfile = fileRepository.save(fileEntity);
        return FileDTO.Response.mapToDto(savedfile);
    }

    public void deleteFile(Long id) {
        if (!fileRepository.existingById(id)) {
            throw new NotFoundException("data not found with id " + id);
        }
        fileRepository.deleteFile(id);
    }
}
