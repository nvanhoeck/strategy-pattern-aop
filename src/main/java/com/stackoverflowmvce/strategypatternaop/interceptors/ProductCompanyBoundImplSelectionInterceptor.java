package com.stackoverflowmvce.strategypatternaop.interceptors;

import com.stackoverflowmvce.strategypatternaop.exceptions.ProductCompanySelectionClassMissingException;
import com.stackoverflowmvce.strategypatternaop.model.AuthenticationDetails;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Method;

@Aspect
@Slf4j
public class ProductCompanyBoundImplSelectionInterceptor implements MethodInterceptor {

    private final ApplicationContext applicationContext;

    public ProductCompanyBoundImplSelectionInterceptor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        String productCompany = getDivision();

        //Class invocation
        Object executionClass = methodInvocation.getThis();
        Object productCompanySpecificInstance;
        Class<?> productCompanySpecificClass;
        try {
            productCompanySpecificInstance = applicationContext.getBean(
                    productCompany + executionClass.getClass().getSimpleName());
            productCompanySpecificClass = productCompanySpecificInstance.getClass();
        } catch (Exception e) {
            throw new ProductCompanySelectionClassMissingException(
                    "No class implementation found for class " + executionClass.getClass()
                            .getSimpleName() + " and productcompany " + productCompany);
        }
        //method invocation
        String methodName = methodInvocation.getMethod().getName();
        Class<?>[] paramClasses =
                new Class<?>[methodInvocation.getMethod().getParameterTypes().length];
        for (int paramIndex = 0; paramIndex < methodInvocation.getMethod()
                .getParameterTypes().length; paramIndex++) {
            Class<?> parameterType = methodInvocation.getMethod().getParameterTypes()[paramIndex];
            if (parameterType.isPrimitive()) {
                paramClasses[paramIndex] = parameterType;
            } else {
                Class<?> paramClass = Class.forName(parameterType.getName());
                paramClasses[paramIndex] = paramClass;
            }
        }
        Method productCompanySpecificMethod =
                productCompanySpecificClass.getMethod(methodName, paramClasses);
        return productCompanySpecificMethod.invoke(productCompanySpecificInstance,
                methodInvocation.getArguments());

    }

    private String getDivision() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext()
                        .getAuthentication();
        AuthenticationDetails details = (AuthenticationDetails) authentication.getDetails();

        String getDivision = details.getDivision();
        return getDivision;
    }
}
