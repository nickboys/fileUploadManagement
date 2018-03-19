/**
 * xkaisun@gmail.com
 * Copyright (c) 2013-2018 All Rights Reserved.
 */

package com.qf.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Gzip 压缩解压工具封装
 *
 * @author XiaokaiSun
 * @version $Id: GzipUtil.java, v 0.1 2018-02-01 16:15 XiaokaiSun Exp $$
 */
public class GzipUtil {

  /**
   * 默认编码
   */
  public static final String GZIP_ENCODE_UTF_8 = "UTF-8";

  /**
   * 字符串压缩为GZIP字节数组
   */
  public static byte[] compress(String str) {
    return compress(str, GZIP_ENCODE_UTF_8);
  }

  /**
   * 字符串压缩为GZIP字节数组
   */
  public static byte[] compress(String str, String encoding) {
    if (str == null || str.length() == 0) {
      return null;
    }
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    GZIPOutputStream gzip;
    try {
      gzip = new GZIPOutputStream(out);
      gzip.write(str.getBytes(encoding));
      gzip.close();
    } catch (IOException e) {
      throw new RuntimeException("compress error", e);
    }
    return out.toByteArray();
  }


  /**
   * GZIP解压缩
   */
  public static byte[] uncompress(byte[] bytes) {
    if (bytes == null || bytes.length == 0) {
      return null;
    }
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ByteArrayInputStream in = new ByteArrayInputStream(bytes);
    try {
      GZIPInputStream ungzip = new GZIPInputStream(in);
      byte[] buffer = new byte[256];
      int n;
      while ((n = ungzip.read(buffer)) >= 0) {
        out.write(buffer, 0, n);
      }
    } catch (IOException e) {
      throw new RuntimeException("uncompress error", e);
    }

    return out.toByteArray();
  }


  /**
   *
   * @param bytes
   * @return
   */
  public static String uncompressToString(byte[] bytes) {
    return uncompressToString(bytes, GZIP_ENCODE_UTF_8);
  }

  /**
   *
   * @param bytes
   * @param encoding
   * @return
   */
  public static String uncompressToString(byte[] bytes, String encoding) {
    if (bytes == null || bytes.length == 0) {
      return null;
    }
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ByteArrayInputStream in = new ByteArrayInputStream(bytes);
    try {
      GZIPInputStream ungzip = new GZIPInputStream(in);
      byte[] buffer = new byte[256];
      int n;
      while ((n = ungzip.read(buffer)) >= 0) {
        out.write(buffer, 0, n);
      }
      return out.toString(encoding);
    } catch (IOException e) {
      throw new RuntimeException("uncompress error", e);
    }
  }
}