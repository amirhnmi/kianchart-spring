package com.kianchart.kianchart.api.permission.permissions;

import com.kianchart.kianchart.core.exception.DuplicationException;
import com.kianchart.kianchart.database.entity.permissions.Permission;
import com.kianchart.kianchart.database.repository.permissions.PermissionRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class PermissionDTO {
    @Getter @Setter
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

        public void validate(PermissionRepository permissionRepository){
            if (permissionRepository.existsBySlug(this.slug)){
                throw new DuplicationException("slug already exist");
            }
        }

        public Permission mapToEntity(){
            Permission permission = new Permission();
            permission.setName(this.name);
            permission.setSlug(this.slug);
            permission.setDescription(this.description);
            return permission;
        }
    }

    @Getter @Setter
    public static class UpdatePermissionRequest{
        @Size(min = 2, max = 128, message = "name must be between 2 and 128 character")
        private String name;

        @Size(min = 2, max = 128, message = "slug must be between 2 and 128 character")
        private String slug;

        @Size(min = 2, max = 512, message = "description must be between 2 and 128 character")
        private String description;

        public void validate(PermissionRepository permissionRepository){
            if (this.slug != null && permissionRepository.existsBySlug(this.slug)){
                throw new DuplicationException("slug already exist");
            }
        }

        public void mapToEntityForUpdate(Permission permission){
            if (this.name != null) permission.setName(this.name);
            if (this.slug != null) permission.setSlug(this.slug);
            if (this.description != null) permission.setDescription(this.description);
        }
    }

    @Getter @Setter
    public static class Response{
        private Long id;
        private String name;
        private String slug;
        private String description;

        public static Response mapToDto(Permission permission){
            Response dto = new Response();
            dto.setId(permission.getId());
            dto.setName(permission.getName());
            dto.setSlug(permission.getSlug());
            dto.setDescription(permission.getDescription());
            return dto;
        }

        public static List<Response> mapToDtoList(List<Permission> permissions, int skip, int limit){
            return permissions.stream().skip(skip).limit(limit).map(Response::mapToDto).collect(Collectors.toList());
        }
    }
}
