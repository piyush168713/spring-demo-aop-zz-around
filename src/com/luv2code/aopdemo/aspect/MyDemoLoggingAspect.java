package com.luv2code.aopdemo.aspect;

import com.luv2code.aopdemo.Account;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Aspect
@Component
@Order(2)
public class MyDemoLoggingAspect {             // 7527030526   -> DSW Conselling cell

    private Logger myLogger = Logger.getLogger(getClass().getName());

    @Around("execution(* com.luv2code.aopdemo.service.*.getFortune(..))")
    public Object aroundGetFortune(
            ProceedingJoinPoint theProceedingJoinPoint) throws Throwable{

        // print our method we are advising on
        String method = theProceedingJoinPoint.getSignature().toShortString();
        System.out.println("\n\n====>>>> Executing @Around on method: " + method);
        // myLogger.info("\n\n====>>>> Executing @Around on method: " + method);


        // get begin timestamp
        long begin = System.currentTimeMillis();

        // now, let's execute the method
        Object result = theProceedingJoinPoint.proceed();

        // get end timestamp
        long end = System.currentTimeMillis();

        // compute duration snd display it
        long duration = end - begin;
        System.out.println("\n====>>>> Duration: " + duration/1000.00 + "seconds");
        // myLogger.info("\n====>>>> Duration: " + duration/1000.00 + "seconds");


        return result;
    }





    @After("execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))")
    public void afterFinallyFindAccountsService(JoinPoint theJoinPoint){

        // printout which method we are advising on
        String method = theJoinPoint.getSignature().toShortString();
        System.out.println("\n\n====>>>> Executing @After (finally) on method: " + method);
        // myLogger.info("\n\n====>>>> Executing @After (finally) on method: " + method);
    }



    @AfterThrowing(pointcut = "execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))",
            throwing = "theExc")
    public void afterThrowingFindAccountsService(
            JoinPoint theJoinPoint, Throwable theExc){

        // printout which method we are advising on
        String method = theJoinPoint.getSignature().toShortString();
        System.out.println("\n\n====>>>> Executing @AfterThrowing (DemoLogging) on method: " + method);
        // myLogger.info("\n\n====>>>> Executing @AfterThrowing (DemoLogging) on method: " + method);


        // log the exception
        System.out.println("\n\n====>>>> The exception is: " + theExc);
        // myLogger.info("\n\n====>>>> The exception is: " + theExc);

    }




    // add a new advice for @AfterReturning on the findAccounts method ....
    // and this method will run after all the method runs i.e. beforeAddAccountService(), performApiAnalytics(), ...

    @AfterReturning(pointcut = "execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))",
                    returning="result")
    public void afterReturningFindAccountsAdvice(
            JoinPoint theJoinPoint, List<Account> result){

        // print our which method we are advising on
        String method = theJoinPoint.getSignature().toShortString();
        System.out.println("\n\n====>>>> Executing @AfterReturning on method: " + method);


        // print out the results of the method call
        System.out.println("\n\n====>>>> result is: " + result);

        // let's post-process the data... let's modify it: -

        // convert the account names to uppercase
        convertAccountNamesToUpperCase(result);

        // System.out.println("\n\n====>>>> result is: " + result);
        myLogger.info("\n\n====>>>> result is: " + result);


    }

    private void convertAccountNamesToUpperCase(List<Account> result) {

        // loop through accounts
        for (Account tempAccount : result) {

            // get uppercase version of name
            String theUpperCase = tempAccount.getName().toUpperCase();

            // update the name on the account
            tempAccount.setName(theUpperCase);
        }
    }


    // @Before advice

    @Before("com.luv2code.aopdemo.aspect.LuvAopExpressions.forDaoPackageNoGetterSetter()")     // must give fully qualified name
    public void beforeAddAccountService(JoinPoint theJoinPoint){
        System.out.println("\n====>>>> Executing @Before advice on addAccount()");

        // display the method signature
        MethodSignature methodSig = (MethodSignature) theJoinPoint.getSignature();
        System.out.println("Method: " + methodSig);
        // myLogger.info("Method: " + methodSig);


        // display method arguments

        // get args
        Object[] args = theJoinPoint.getArgs();

        // loop thru args
        for(Object tempArg : args){
            System.out.println(tempArg);
            // myLogger.info(tempArg.toString());

            if (tempArg instanceof Account) {
                // downcast and print Account specific stuff

                Account theAccount = (Account) tempArg;
                System.out.println("account name: " + theAccount.getName());
                System.out.println("account level: " + theAccount.getLevel());
            }
        }

    }

}
