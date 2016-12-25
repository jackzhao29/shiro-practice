package com.coldface.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/**
 * 类WebTools.java的实现描述：TODO 类实现描述
 * @author coldface
 * @date 2016年10月10日上午11:45:04
 */
public class WebTools {
  private static Logger logger = LoggerFactory.getLogger(WebTools.class);
  /**
   * 直接给response发送json信息
   * @param response
   * @param text
   */
  public static void sendJsonResponse(HttpServletResponse response, String json) {
      // 设置状态码, 200， 当做一个正常返回
      response.setStatus(HttpStatus.OK.value());
      // 设置ContentType
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.setCharacterEncoding("UTF-8");
      response.setHeader("Cache-Control", "no-cache");
      try {
          response.getWriter().write(json);
      } catch (IOException e) {
          logger.error("getLocalHostAddress error.", e);
      }
  }

}
