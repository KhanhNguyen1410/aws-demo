package com.example.aws.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LogUtil {
  public LogUtil() {
  }

  public static String printLogStackTrace(Exception e) {
    try {
      StringWriter errors = new StringWriter();
      e.printStackTrace(new PrintWriter(errors));
      return errors.toString();
    } catch (Exception var2) {
      return null;
    }
  }
}
