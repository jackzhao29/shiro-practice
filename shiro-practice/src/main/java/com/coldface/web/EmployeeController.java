package com.coldface.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类EmployeeController.java的实现描述：TODO 类实现描述
 * @author zhaofei
 * @date 2016年11月15日下午2:49:38
 */
@RestController
@RequestMapping("/test")
public class EmployeeController {
  
  @RequestMapping("/name")
  public void test(String name){
    System.out.println(name);
  }

}
