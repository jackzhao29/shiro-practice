package com.coldface.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.coldface.entity.DataResult;
import com.coldface.entity.EmployeeBo;

/**
 * 类EmployeeApi.java的实现描述：对外提供的接口业务类
 * 
 * @author coldface
 * @date 2016年10月18日下午5:39:54
 */
@RestController
@RequestMapping(value = "/api")
public class EmployeeApi {

  @RequestMapping(value = "/push")
  public DataResult<String> pushEmployeeInfo(String data) {
    DataResult<String> dataResult = new DataResult<String>();
    EmployeeBo bo = JSON.parseObject(data, EmployeeBo.class);
    System.out.println(JSON.toJSONString(bo));
    dataResult.setSuccess("success");
    dataResult.setData("true");
    return dataResult;

  }

}
