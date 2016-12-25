package com.coldface.shiro;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.coldface.entity.DataResult;
import com.coldface.util.Constants;
import com.coldface.util.MapUtils;
import com.coldface.util.WebTools;


/**
 * 类ValidityCheckFilter.java的实现描述：对外暴露接口验证 接口参数: data、sign、source sign为secret+param进行MD5校验再base64后的值
 * source为具体的调用方标示
 * 
 * @author coldface
 * @date 2016年10月10日上午11:39:32
 */
public class ValidityCheckFilter extends AccessControlFilter {
  private Logger logger = LoggerFactory.getLogger(ValidityCheckFilter.class);
  private Map<String, String> secretMap;

  {
    secretMap = new HashMap<String, String>();
    secretMap.put("secret", Constants.SECRET_API);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.apache.shiro.web.filter.AccessControlFilter#isAccessAllowed(javax.servlet.ServletRequest,
   * javax.servlet.ServletResponse, java.lang.Object)
   */
  @Override
  protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse,
      Object arg2) throws Exception {
    String source = "";
    String validateSign = "";
    String sign = "";
    String data = "";
    try {
      HttpServletRequest request = (HttpServletRequest) servletRequest;
      source = request.getParameter("source");
      sign = request.getParameter("sign");
      data = MapUtils.toMap(request.getParameterMap()).get("data");

      // 接口调用必须包含的两个参数:sign签名,source调用方
      if (StringUtils.isBlank(sign) || StringUtils.isBlank(source)) {
        return false;
      }
      String secret = secretMap.get(source);
      logger.info("secret" + secret);
      try {
        validateSign =
            Base64.encodeBase64String(DigestUtils.md5Hex(secret + data).getBytes("UTF-8"));
      } catch (UnsupportedEncodingException e) {
        logger.error("validate filter encode error: " + e.getMessage());
      }
      if (!validateSign.equals(sign)) {
        logger.warn("validate fail. sign=" + sign + ", validateSign=" + validateSign + ", source="
            + source + ", data=" + data);
        return false;
      }
    } catch (Exception e) {
      logger.info("validate filter check error!" + e.getMessage());
    }
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.apache.shiro.web.filter.AccessControlFilter#onAccessDenied(javax.servlet.ServletRequest,
   * javax.servlet.ServletResponse)
   */
  @SuppressWarnings({"rawtypes", "unused"})
  @Override
  protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse)
      throws Exception {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    DataResult result = new DataResult();
    result.setErrorCode("404");
    String json = JSON.toJSONString(result);
    WebTools.sendJsonResponse(response, json);
    return false;
  }

}
