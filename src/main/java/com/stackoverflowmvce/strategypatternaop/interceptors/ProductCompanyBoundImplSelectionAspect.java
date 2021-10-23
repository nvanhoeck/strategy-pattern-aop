package com.stackoverflowmvce.strategypatternaop.interceptors;

import com.stackoverflowmvce.strategypatternaop.exceptions.ProductCompanySelectionClassMissingException;
import com.stackoverflowmvce.strategypatternaop.model.AuthenticationDetails;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
@Slf4j
public class ProductCompanyBoundImplSelectionAspect {
    @Autowired
    private ApplicationContext applicationContext;

    @Around("@annotation(com.stackoverflowmvce.strategypatternaop.annotations.ProductCompanyImplSelection)")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        String productCompany = getDivision();

        Object executionClass = joinPoint.getTarget();
        Object productCompanyInstance;
        Class<?> productCompanyClass;
        try {
            String productCompanyBeanName = productCompany + executionClass.getClass().getSimpleName();
            productCompanyInstance = applicationContext.getBean(productCompanyBeanName);
            productCompanyClass = productCompanyInstance.getClass();
        }
        catch (Exception e) {
            throw new ProductCompanySelectionClassMissingException(
                "No class implementation found for class " + executionClass.getClass().getSimpleName() +
                " and productcompany " + productCompany,
                e
            );
        }

        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        return productCompanyClass
            .getMethod(method.getName(), method.getParameterTypes())
            .invoke(productCompanyInstance, joinPoint.getArgs());
    }

    private String getDivision() {
        UsernamePasswordAuthenticationToken authenticationToken =
            (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        AuthenticationDetails authenticationDetails = (AuthenticationDetails) authenticationToken.getDetails();
        return authenticationDetails.getDivision();
    }
}
