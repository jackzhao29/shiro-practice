package com.coldface.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


/**
 * 类AspceJAdvice.java的实现描述：TODO 类实现描述
 * 
 * @author coldface
 * @date 2016年12月20日下午7:27:10
 */
@Aspect
@Component
public class AspceJAdvice {
  @Pointcut("execution(* *..impl..*(..)) || execution(* *..dao..*(..)) || execution(* *..web.*(..))")
  private void aspectjMethod() {

  }

  @AfterThrowing(value = "aspectjMethod()", throwing = "e")
  public void doException(JoinPoint joinPoint, Throwable e) throws Throwable {
    System.out.println("AspceJAdvice=" + joinPoint.getTarget().getClass() + "."
        + joinPoint.getSignature().getName() + ";" + e.toString());
  }

}
