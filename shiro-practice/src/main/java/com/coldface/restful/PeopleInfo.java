package com.coldface.restful;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.coldface.entity.DataResult;

import jersey.repackaged.com.google.common.collect.Lists;
import jersey.repackaged.com.google.common.collect.Maps;

/**
 * 类PeopleInfo.java的实现描述：TODO 类实现描述
 * @author zhaofei
 * @date 2016年12月23日下午2:34:02
 */
@RestController
@RequestMapping("service")
public class PeopleInfo {
  
  @RequestMapping(value = "/info", method = RequestMethod.GET)
  public DataResult<List<String>> getInfo(){
    DataResult<List<String>> result= new DataResult<List<String>>();
    List<String> list=Lists.newArrayList();
    list.add("小李");
    list.add("小王");
    list.add("小张");
    result.setData(list);
    return result;
  }
  
  @RequestMapping(value = "/info/{name}", method = RequestMethod.GET)
  public DataResult<String> getInfo(@PathVariable String name){
    DataResult<String> result= new DataResult<String>();
    Map<String,String> maps=Maps.newHashMap();
    maps.put("小李", "北京市海淀区28号");
    maps.put("小王","上海市1号");
    maps.put("小张","海南省三亚市");
    result.setData(maps.get(name));
    return result;
  }
 
}
