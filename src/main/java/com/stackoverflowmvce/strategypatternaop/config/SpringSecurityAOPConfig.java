package com.stackoverflowmvce.strategypatternaop.config;

import com.stackoverflowmvce.strategypatternaop.interceptors.ProductCompanyBoundImplSelectionInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan("com.stackoverflowmvce.strategypatternaop.*")
public class SpringSecurityAOPConfig {

    @Bean
    public Advisor productCompanyBoundImplSelectionAdvisor(ApplicationContext applicationContext) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(
                "@annotation(com.stackoverflowmvce.strategypatternaop.annotations.ProductCompanyImplSelection)");
        DefaultPointcutAdvisor pointcutAdvisor =
                new DefaultPointcutAdvisor(pointcut, new ProductCompanyBoundImplSelectionInterceptor(
                        applicationContext));
        return pointcutAdvisor;
    }

}
