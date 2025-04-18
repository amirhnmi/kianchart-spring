package com.kianchart.kianchart.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    public static class CreateFileRequest {
        public byte[] getContent() {
            return content;
        }

        public void setContent(byte[] content) {
            this.content = content;
        }

        public Long getCreatorId() {
            return creatorId;
        }

        public void setCreatorId(Long creatorId) {
            this.creatorId = creatorId;
        }

        public FileType getType() {
            return type;
        }

        public void setType(FileType type) {
            this.type = type;
        }

        private byte[] content;
        private Long creatorId;
        private FileType type;
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
    }
}
