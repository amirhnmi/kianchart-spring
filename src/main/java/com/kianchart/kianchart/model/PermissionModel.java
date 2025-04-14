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

public class PermissionModel {
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

        public PermissionEntity mapToEntity(){
            PermissionEntity permissionEntity = new PermissionEntity();
            permissionEntity.setName(this.name);
            permissionEntity.setSlug(this.slug);
            permissionEntity.setDescription(this.description);
            return permissionEntity;
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

        public void mapToEntityForUpdate(PermissionEntity permissionEntity){
            if (this.name != null) permissionEntity.setName(this.name);
            if (this.slug != null) permissionEntity.setSlug(this.slug);
            if (this.description != null) permissionEntity.setDescription(this.description);
        }
    }

    @Getter @Setter
    public static class Response{
        private Long id;
        private String name;
        private String slug;
        private String description;

        public static Response mapToDto(PermissionEntity permissionEntity){
            Response dto = new Response();
            dto.setId(permissionEntity.getId());
            dto.setName(permissionEntity.getName());
            dto.setSlug(permissionEntity.getSlug());
            dto.setDescription(permissionEntity.getDescription());
            return dto;
        }

        public static List<Response> mapToDtoList(List<PermissionEntity> permissionEntities, int skip, int limit){
            return permissionEntities.stream().skip(skip).limit(limit).map(Response::mapToDto).collect(Collectors.toList());
        }
    }
}
