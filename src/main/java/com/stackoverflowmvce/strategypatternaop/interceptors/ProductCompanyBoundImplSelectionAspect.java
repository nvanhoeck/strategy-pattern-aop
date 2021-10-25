package com.stackoverflowmvce.strategypatternaop.interceptors;

import com.stackoverflowmvce.strategypatternaop.annotations.ProductCompanyImplSelection;
import com.stackoverflowmvce.strategypatternaop.exceptions.ProductCompanySelectionClassMissingException;
import com.stackoverflowmvce.strategypatternaop.model.AuthenticationDetails;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
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

    @Around("@within(com.stackoverflowmvce.strategypatternaop.annotations.ProductCompanyDefaultImpl)")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        if (AnnotationUtils.findAnnotation(method, ProductCompanyImplSelection.class) == null)
            return joinPoint.proceed();

        String productCompany = getDivision();
        Object executionInstance = joinPoint.getTarget();
        String executionClassSimpleName = executionInstance.getClass().getSimpleName();

        // Determine strategy class
        Object productCompanyInstance;
        Class<?> productCompanyClass;
        try {
            String productCompanyBeanName = executionClassSimpleName.replaceFirst("^(Default|Base|Standard)", productCompany);
            productCompanyInstance = applicationContext.getBean(productCompanyBeanName);
            productCompanyClass = productCompanyInstance.getClass();
        }
        catch (Exception cause) {
            String errorMessage =
                "No class implementation found for class " + executionClassSimpleName +
                " and productcompany " + productCompany;
            throw new ProductCompanySelectionClassMissingException(errorMessage, cause);
        }

        // Invoke strategy method
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
