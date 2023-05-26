package com.posgrado.ecommerce.service;

import com.posgrado.ecommerce.dto.RoleDto;
import com.posgrado.ecommerce.entity.Role;
import com.posgrado.ecommerce.exception.EntityNotFoundException;
import com.posgrado.ecommerce.exception.RoleAlreadyTaken;
import com.posgrado.ecommerce.mapper.RoleMapper;
import com.posgrado.ecommerce.repository.RoleRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

  private RoleRepository roleRepository;
  private RoleMapper roleMapper;

  @Override
  public Role create(RoleDto dto) {
    String nameToUpper = dto.getName().toUpperCase();
    boolean existRole = existRoleName(nameToUpper);

    if (existRole) {
      throw new RoleAlreadyTaken(nameToUpper);
    }

    Role role = roleMapper.fromDto(dto);
    return roleRepository.save(role);
  }

  @Override
  public Role getByName(String name) {
    return roleRepository.findByName(name)
        .orElseThrow(() -> new EntityNotFoundException("Role not found"));
  }

  @Override
  public List<Role> getAll() {
    return roleRepository.findAll();
  }

  @Override
  public boolean existRoleName(String name) {
    return roleRepository.findByName(name).isPresent();
  }
}
