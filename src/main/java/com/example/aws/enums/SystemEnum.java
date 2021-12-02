package com.example.aws.enums;

public class SystemEnum {

  public enum RoleType {
    ADMIN("ADMIN"), MANAGER("MANAGER"), USER("USER"), ADMIN_BEAN("ADMIN_BEAN");

    RoleType(String roleType) {
      this.roleType = roleType;
    }

    private String roleType;

    public String value() {
      return roleType;
    }
  }

  public enum Scopes {
    REFRESH_TOKEN;

    public String authority() {
      return "ROLE_" + this.name();
    }
  }
}
