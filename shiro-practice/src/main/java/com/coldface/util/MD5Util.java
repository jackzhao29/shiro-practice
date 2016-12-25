package com.coldface.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MD5Util {
  private static final Logger log = LoggerFactory.getLogger(MD5Util.class);
  private static final ThreadLocal<MessageDigest> MESSAGE_DIGEST_CACHE =
      new ThreadLocal<MessageDigest>();
  private static char md5Chars[] =
      {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

  public static MessageDigest getMessageDigest() throws NoSuchAlgorithmException {
    MessageDigest messagedigest = MESSAGE_DIGEST_CACHE.get();
    if (messagedigest == null) {
      messagedigest = MessageDigest.getInstance("MD5");
      MESSAGE_DIGEST_CACHE.set(messagedigest);
    }
    return messagedigest;
  }

  /* 获取一个字符串的md5码 */
  public static String getMD5Str(String str) {
    try {
      MessageDigest messagedigest = getMessageDigest();
      messagedigest.update(str.getBytes("UTF-8"));
      return bufferToHex(messagedigest.digest());
    } catch (Exception e) {
      log.error("getMD5Str error", e);
      throw new RuntimeException(e);
    }
  }

  public static String getMD5File(File file) {
    if (!file.exists()) {
      return "";
    }
    FileInputStream in;
    try {
      in = new FileInputStream(file);
      FileChannel ch = in.getChannel();
      MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
      getMessageDigest().update(byteBuffer);
      return bufferToHex(getMessageDigest().digest());
    } catch (FileNotFoundException e) {
      log.error("[MD5 file]", e);
    } catch (NoSuchAlgorithmException e) {
      log.error("[MD5 file]", e);
    } catch (IOException e) {
      log.error("[MD5 file]", e);
    }
    return "";
  }

  public static boolean checkMD5File(File file, String md5) {
    return getMD5File(file).equals(md5) ? true : false;
  }

  /* 验证一个字符串的MD5和给定MD5码是否相等 */
  public static boolean checkMD5Str(String str, String md5) throws Exception {
    return getMD5Str(str).equals(md5) ? true : false;
  }

  private static String bufferToHex(byte bytes[]) {
    return bufferToHex(bytes, 0, bytes.length);
  }

  private static String bufferToHex(byte bytes[], int m, int n) {
    StringBuffer stringbuffer = new StringBuffer(2 * n);
    int k = m + n;
    for (int l = m; l < k; l++) {
      appendHexPair(bytes[l], stringbuffer);
    }
    return stringbuffer.toString();
  }

  private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
    char c0 = md5Chars[(bt & 0xf0) >> 4];
    char c1 = md5Chars[bt & 0xf];
    stringbuffer.append(c0);
    stringbuffer.append(c1);
  }

  public static String signature(Map<String, Object> data, String token) {

    if (data != null && !data.isEmpty() && StringUtils.isNotBlank(token)) {

      List<String> keys = new ArrayList<String>(data.keySet());
      Collections.sort(keys);

      List<String> keyValueList = new ArrayList<String>();
      for (String key : keys) {
        Object value = data.get(key);
        if (value != null && !"".equals(value) && !Boolean.FALSE.equals(value)) {
          keyValueList.add(key + "=" + value);
        }
      }

      String queryString = StringUtils.join(keyValueList, "&");
      return MD5Util.getMD5Str(queryString + token);

    } else {
      return "";
    }
  }

  public static String signatureZues(Map<String, String> data, String token) {

    if (data != null && !data.isEmpty() && StringUtils.isNotBlank(token)) {

      data.put("key", token);

      List<String> keys = new ArrayList<String>(data.keySet());
      Collections.sort(keys);

      List<String> keyValueList = new ArrayList<String>();
      for (String key : keys) {
        String value = data.get(key);
        if (value != null) {
          keyValueList.add(key + "=" + value);
        }
      }

      String queryString = StringUtils.join(keyValueList, "&");
      return MD5Util.getMD5Str(queryString);

    } else {
      return "";
    }
  }

  public static String signature(String timestamp, String token) {
    return StringUtils.isBlank(timestamp) || StringUtils.isBlank(token) ? ""
        : getMD5Str(timestamp + token);
  }

  /*
   * 该方法用于和php对接数据
   **/
  public static String signaturePhp(String str) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(str.getBytes());
      StringBuffer sb = new StringBuffer();
      byte[] bytes = md.digest();
      for (int i = 0; i < bytes.length; i++) {
        int b = bytes[i] & 0xFF;
        if (b < 0x10) {
          sb.append('0');
        }
        sb.append(Integer.toHexString(b));
      }
      return sb.toString();
    } catch (Exception e) {
      return "";
    }
  }

  public static void main(String[] args) {
    System.out.println(genRandomNum(16));
  }

  /**
   * 生成随机密码
   *
   * @param zhaofei 生成的密码的总长度
   * @return 密码的字符串
   */
  public static String genRandomNum(int pwd_len) {
    // 35是因为数组是从0开始的，26个字母+10个数字
    final int maxNum = 36;
    int i; // 生成的随机数
    int count = 0; // 生成的密码的长度
    char[] str = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
        'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9'};
    StringBuffer pwd = new StringBuffer("");
    Random r = new Random();
    while (count < pwd_len) {
      // 生成随机数，取绝对值，防止生成负数，
      i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
      if (i >= 0 && i < str.length) {
        pwd.append(str[i]);
        count++;
      }
    }
    return pwd.toString();
  }
}
