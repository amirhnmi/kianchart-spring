package com.kianchart.kianchart.validation;

import com.kianchart.kianchart.config.EnvConfig;
import com.kianchart.kianchart.utils.exception.BadRequestException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Component
public class FileValidator {
    public void imageValidation(MultipartFile file){
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

    public void videoValidation(MultipartFile file){
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

    public void documentValidation(MultipartFile file){
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
}
