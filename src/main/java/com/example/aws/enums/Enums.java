package com.example.aws.enums;

public class Enums {
  public enum ResponseStatus {
    SUCCESS("Success"), ERROR("Error");

    ResponseStatus(String status) {
      this.status = status;
    }

    public String getStatus() {
      return status;
    }

    private String status;

  }
}
