package com.kianchart.kianchart.model;

import com.kianchart.kianchart.config.EnvConfig;
import com.kianchart.kianchart.entity.UserEntity;
import com.kianchart.kianchart.enums.FileType;
import com.kianchart.kianchart.utils.exception.BadRequestException;
import com.kianchart.kianchart.entity.FileEntity;
import com.kianchart.kianchart.repository.FileRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileModel {
    @Getter
    @Setter
    public static class CreateFileRequest {
//        @NotNull(message = "file is required")
//        private MultipartFile file;
//
//        @NotNull(message = "creatorId is required")
//        private Long creatorId;

        public static void imageValidation(MultipartFile file){
            List<String> validImageMimeType = Arrays.asList("image/jpeg", "image/png", "image/webp", "image/jpg");
            if (!validImageMimeType.contains(file.getContentType())){
                throw new BadRequestException(
                        "invalid file type, valid types: " + String.join(",", validImageMimeType)
                );
            }
            long imageSizeMB = file.getSize() / (1024 * 1024);
            int maxImageSize = EnvConfig.getMAX_IMAGE_SIZE();
            if (imageSizeMB > maxImageSize){
                throw new BadRequestException(
                        "The image size should not be more than " + maxImageSize + " MB. your file size: " + imageSizeMB + " MB"
                );
            }
        }

        public static void videoValidation(MultipartFile file){
            List<String> validVideoMimeType = Arrays.asList(
                    "video/3gpp",
                    "video/mp2t",
                    "video/mp4",
                    "video/mpeg",
                    "video/quicktime",
                    "video/webm",
                    "video/x-flv",
                    "video/x-m4v",
                    "video/x-mng",
                    "video/x-ms-asf",
                    "video/x-ms-wmv",
                    "video/x-msvideo"
            );
            if (!validVideoMimeType.contains(file.getContentType())){
                throw new BadRequestException(
                        "invalid file type, valid types: " + String.join(",", validVideoMimeType)
                );
            }
            long videoSizeMB = file.getSize() / (1024 * 1024);
            int maxVideoSize = EnvConfig.getMAX_VIDEO_SIZE();
            if (videoSizeMB > maxVideoSize){
                throw new BadRequestException(
                        "The image size should not be more than " + maxVideoSize + " MB. your file size: " + videoSizeMB + " MB"
                );
            }
        }

        public static void documentValidation(MultipartFile file){
            List<String> documentMimeType = Arrays.asList(
                    "text/html",
                    "text/xml",
                    "text/plain",
                    "text/javascript",
                    "application/json",
                    "application/msword",
                    "application/pdf",
                    "application/vnd.ms-excel"
            );
            if (!documentMimeType.contains(file.getContentType())){
                throw new BadRequestException(
                        "invalid file type, valid types: " + String.join(",", documentMimeType)
                );
            }
            long documentSizeMB = file.getSize() / (1024 * 1024);
            int maxDocumentSize = EnvConfig.getMAX_DOCUMENT_SIZE();
            if (documentSizeMB > maxDocumentSize){
                throw new BadRequestException(
                        "The image size should not be more than " + maxDocumentSize + " MB. your file size: " + documentSizeMB + " MB"
                );
            }
        }

        public static FileEntity mapToEntity(FileRepository fileRepository, MultipartFile file, Long creatorId, FileType fileType) {
            FileEntity fileEntity = new FileEntity();
            fileEntity.setFile(file.getOriginalFilename());
            fileEntity.setType(fileType);
            fileEntity.setSize(file.getSize());
            fileEntity.setExtension(file.getContentType());

            UserEntity userEntity = fileRepository.findOneUser(creatorId);
            fileEntity.setCreator(userEntity);
            return fileEntity;
        }
    }

    @Getter
    @Setter
    public static class Response {
        private Long id;
        private String file;
        private Long size;
        private FileType type;
        private String extension;
        private String path;
        private UserModel.Response creator;

        public static Response mapToDto(FileEntity file) {
            Response dto = new Response();
            dto.setId(file.getId());
            dto.setFile(file.getFile());
            dto.setSize(file.getSize());
            dto.setType(file.getType());
            dto.setExtension(file.getExtension());
            dto.setPath(EnvConfig.getBASE_URL() + "/uploads/"+ file.getType() +"/"+ file.getFile());
//            dto.setCreator(UserModel.Response.mapToDto(file.getCreator()));
            return dto;
        }

        public static List<Response> mapToDtoList(List<FileEntity> files, int skip, int limit){
            return files.stream().skip(skip).limit(limit).map(Response::mapToDto).collect(Collectors.toList());
        }
    }


}
