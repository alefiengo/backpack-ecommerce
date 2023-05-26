package com.posgrado.ecommerce.exception;

public class RoleAlreadyTaken extends RuntimeException {

  private static final String ERROR_MESSAGE = "Role with name %s already exists.";

  public RoleAlreadyTaken(String name) {
    super(String.format(ERROR_MESSAGE, name));
  }

}
