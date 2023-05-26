package com.posgrado.ecommerce.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmailNotification {

  private String to;
  private String subject;
  private String body;
  private boolean hasTemplate;
}
