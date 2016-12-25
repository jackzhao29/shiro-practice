package com.coldface.restful;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
/**
 * 类UserInfo.java的实现描述：TODO 类实现描述
 * @author zhaofei
 * @date 2016年12月20日下午7:30:17
 */
@Path("/UserInfoService")
public class UserInfo {
  
  @GET
  @Path("/name/{u}")
  @Produces(MediaType.TEXT_XML)
  public String userName(@PathParam("u") String u){
    String name=u;
    System.out.println(name);
    return "<User>"+"<Name>"+name+"</Name>"+"</User>";
  }
  
  @GET
  @Path("/age/{a}")
  @Produces(MediaType.APPLICATION_JSON)
  public String getAge(@PathParam("a") int a){
    System.out.println(a);
    return String .valueOf(a);
  }
  
  

}
