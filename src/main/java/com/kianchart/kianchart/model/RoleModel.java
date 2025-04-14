package com.kianchart.kianchart.model;

import com.kianchart.kianchart.entity.RoleEntity;
import com.kianchart.kianchart.utils.exception.DuplicationException;
import com.kianchart.kianchart.repository.RoleRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class RoleModel {
    @Getter
    @Setter
    public static class CreateRoleRequest {
        @NotBlank(message = "name is required")
        @Size(min = 2, max = 128, message = "name must be between 2 and 128")
        private String name;

        @NotBlank(message = "slug is required")
        @Size(min = 2, max = 128, message = "slug must be between 2 and 128")
        private String slug;

        @NotBlank(message = "description is required")
        @Size(min = 2, max = 512, message = "description must be between 2 and 512")
        private String description;

        public void validate(RoleRepository roleRepository) {
            if (roleRepository.existsBySlug(this.slug)) {
                throw new DuplicationException("slug already exists");
            }
        }

        public RoleEntity mapToEntity() {
            RoleEntity roleEntity = new RoleEntity();

            roleEntity.setName(this.name);
            roleEntity.setSlug(this.slug);
            roleEntity.setDescription(this.description);
            return roleEntity;
        }
    }

    @Getter
    @Setter
    public static class UpdateRoleRequest {
        @Size(min = 2, max = 128, message = "name must be between 2 and 128")
        private String name;

        @Size(min = 2, max = 128, message = "slug must be between 2 and 128")
        private String slug;

        @Size(min = 2, max = 512, message = "description must be between 2 and 512")
        private String description;

        public void validate(RoleRepository roleRepository) {
            if (this.slug != null && roleRepository.existsBySlug(this.slug)) {
                throw new DuplicationException("slug already exists");
            }
        }

        public void MapToEntityForUpdate(RoleEntity roleEntity) {
            if (this.name != null) roleEntity.setName(this.name);
            if (this.slug != null) roleEntity.setSlug(this.slug);
            if (this.description != null) roleEntity.setDescription(this.description);
        }
    }

    @Getter
    @Setter
    public static class Response {
        private Long id;
        private String name;
        private String slug;
        private String description;

        public static RoleModel.Response mapToDto(RoleEntity roleEntity) {
            Response dto = new Response();
            dto.setId(roleEntity.getId());
            dto.setName(roleEntity.getName());
            dto.setSlug(roleEntity.getSlug());
            dto.setDescription(roleEntity.getDescription());
            return dto;
        }

        public static List<Response> mapToDtoList(List<RoleEntity> roleEntities, int skip, int limit) {
            return roleEntities.stream().skip(skip).limit(limit).map(Response::mapToDto).collect(Collectors.toList());
        }
    }


}
