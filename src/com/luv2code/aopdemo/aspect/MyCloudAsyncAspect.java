package com.luv2code.aopdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class MyCloudAsyncAspect {

    @AfterThrowing(pointcut = "execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))",
            throwing = "theExc")
    public void afterThrowingFindCloudService(
            JoinPoint theJoinPoint, Throwable theExc){

        // printout which method we are advising on
        String method = theJoinPoint.getSignature().toShortString();
        System.out.println("\n\n====>>>> Executing @AfterThrowing (CloudAsync) on method: " + method);

        // log the exception
        System.out.println("\n\n====>>>> The exception is: " + theExc);
    }

    @Before("com.luv2code.aopdemo.aspect.LuvAopExpressions.forDaoPackageNoGetterSetter()")     // must give fully qualified name
    public void logToCloudAsync(){
        System.out.println("\n====>>>> Logging to Cloud in async fashion");
    }
}
