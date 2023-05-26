package com.posgrado.ecommerce.mapper;

import com.posgrado.ecommerce.dto.RoleDto;
import com.posgrado.ecommerce.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

  public Role fromDto(RoleDto dto) {
    Role role = new Role();
    role.setName(dto.getName().toUpperCase());
    role.setDescription(dto.getDescription());
    return role;
  }
}
