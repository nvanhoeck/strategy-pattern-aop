package com.stackoverflowmvce.strategypatternaop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@ComponentScan("com.stackoverflowmvce.strategypatternaop.*")
public class StrategyPatternAopApplication {

    public static void main(String[] args) {
        SpringApplication.run(StrategyPatternAopApplication.class, args);
    }

}
