package com.coldface.aop;

import java.util.Date;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.StopWatch;


/**
 * 类MethodTimeAdvice.java的实现描述：记录方法的时间
 * @author coldface
 * @date 2016年11月15日上午11:26:22
 */

public class MethodTimeAdvice implements MethodInterceptor {

  /* (non-Javadoc)
   * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
   */
  @SuppressWarnings("rawtypes")
  public Object invoke(MethodInvocation invocation) throws Throwable {
    // TODO Auto-generated method stub
    System.out.println("invoke");
    Object result=null;
    try{
    //用 commons-lang 提供的 StopWatch 计时，Spring 也提供了一个 StopWatch
      StopWatch clock = new StopWatch();
      clock.start(); //计时开始
      System.out.println("startTime="+new Date());
      result = invocation.proceed();
      System.out.println("endTime="+new Date());
      clock.stop();  //计时结束

      //方法参数类型，转换成简单类型
      Class[] params = invocation.getMethod().getParameterTypes();
      String[] simpleParams = new String[params.length];
      for (int i = 0; i < params.length; i++) {
          simpleParams[i] = params[i].getSimpleName();
      }

      System.out.println("Takes:" + clock.getTotalTimeSeconds() + " s ["
              + invocation.getThis().getClass().getName() + "================"
              + invocation.getMethod().getName() + "("+StringUtils.join(simpleParams,",")+")] ");
    }catch(Exception ex){
      ex.printStackTrace();
    }
    return result;
  }

}
