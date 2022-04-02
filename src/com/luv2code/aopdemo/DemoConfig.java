package com.luv2code.aopdemo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy          // It can use autoproxy with the AOP
@ComponentScan("com.luv2code.aopdemo")          // Component scan for components and aspects
public class DemoConfig {

}