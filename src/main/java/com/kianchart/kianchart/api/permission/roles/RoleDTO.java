package com.kianchart.kianchart.api.permission.roles;

import com.kianchart.kianchart.core.exception.DuplicationException;
import com.kianchart.kianchart.database.entity.permissions.Role;
import com.kianchart.kianchart.database.repository.permissions.RoleRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class RoleDTO {
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

        public Role mapToEntity() {
            Role role = new Role();

            role.setName(this.name);
            role.setSlug(this.slug);
            role.setDescription(this.description);
            return role;
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

        public void MapToEntityForUpdate(Role role) {
            if (this.name != null) role.setName(this.name);
            if (this.slug != null) role.setSlug(this.slug);
            if (this.description != null) role.setDescription(this.description);
        }
    }

    @Getter
    @Setter
    public static class Response {
        private Long id;
        private String name;
        private String slug;
        private String description;

        public static Response mapToDto(Role role) {
            Response dto = new Response();
            dto.setId(role.getId());
            dto.setName(role.getName());
            dto.setSlug(role.getSlug());
            dto.setDescription(role.getDescription());
            return dto;
        }

        public static List<Response> mapToDtoList(List<Role> roles, int skip, int limit) {
            return roles.stream().skip(skip).limit(limit).map(Response::mapToDto).collect(Collectors.toList());
        }
    }


}
