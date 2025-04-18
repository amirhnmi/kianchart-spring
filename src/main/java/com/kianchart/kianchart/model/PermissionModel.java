package com.kianchart.kianchart.model;

import com.kianchart.kianchart.entity.PermissionEntity;
import com.kianchart.kianchart.utils.exception.DuplicationException;
import com.kianchart.kianchart.repository.PermissionRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PermissionModel {

    @Getter
    @Setter
    public static class CreatePermissionRequest{
        @NotBlank(message = "name is required")
        @Size(min = 2, max = 128, message = "name must be between 2 and 128 character")
        private String name;

        @NotBlank(message = "slug is required")
        @Size(min = 2, max = 128, message = "slug must be between 2 and 128 character")
        private String slug;

        @NotBlank(message = "description is required")
        @Size(min = 2, max = 512, message = "description must be between 2 and 128 character")
        private String description;
    }

    @Getter
    @Setter
    public static class UpdatePermissionRequest{
        @Size(min = 2, max = 128, message = "name must be between 2 and 128 character")
        private String name;

        @Size(min = 2, max = 128, message = "slug must be between 2 and 128 character")
        private String slug;

        @Size(min = 2, max = 512, message = "description must be between 2 and 128 character")
        private String description;
    }

    @Getter @Setter
    public static class Response{
        private Long id;
        private String name;
        private String slug;
        private String description;
    }
}
