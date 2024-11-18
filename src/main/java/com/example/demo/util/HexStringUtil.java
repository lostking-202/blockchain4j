package com.example.demo.util;


public class HexStringUtil {
  private static final String HEX_PREFIX="0x";
  public static String tryFromHexString(String stringValue){
    return stringValue.startsWith(HEX_PREFIX)?stringValue.substring(HEX_PREFIX.length()):stringValue;
  }
}
