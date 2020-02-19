package com.atoncorp.framework.audit;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class ApiAuditAspect {

    @Before("execution(*  *..*Controller.*(..))") // execution(private/public returnType..ClassName.methodName.(parameter))
    public void auditRequestBody(JoinPoint joinPoint){
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attr.getRequest();

        System.out.println("request uri : " + request.getRequestURI());

        Object[] args = joinPoint.getArgs();
        //args

        System.out.println("-----시작입니다.----------");
    }

    @AfterReturning(value = "execution(*  *..*Controller.*(..))", returning = "returnValue") // execution(private/public returnType..ClassName.methodName.(parameter))
    public void auditResponseBody(JoinPoint joinPoint, Object returnValue){
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attr.getRequest();

        System.out.println("request uri : " + request.getRequestURI());

        System.out.println("return value : " + returnValue.toString());


        System.out.println("-----리턴했습니다.------");
    }
}
