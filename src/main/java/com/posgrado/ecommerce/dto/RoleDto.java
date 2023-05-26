package com.posgrado.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoleDto {

  @NotBlank(message = "{role.name.size.not-blank}")
  @Size(min = 5, max = 20, message = "{role.name.size}")
  private String name;

  @NotBlank(message = "{role.description.not-blank}")
  private String description;

}
