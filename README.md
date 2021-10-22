# strategy-pattern-aop

We have a situation where we want to apply the strategy pattern based on method level. So we want an interface or abstract class which has methods, and based on your security role you are allowed to execute a different implementation.

We use Spring AOP annotations, to execute the functionality to determine which class/functionality should be used:

Annotation Class

@Inherited
@Target({METHOD})
@Retention(RUNTIME)
public @interface ProductCompanyImplSelection {

}
Base Class to derive the standard methods from

@Component
public class StrategyPattern {

    @ProductCompanyImplSelection
    public String executeMethod(TestObject value, int primitive, String valueString) {
        return null;
    }

}
Multiple "Strategy Implementation" Classes

@Component
public class AIPStrategyPattern {

    public String executeMethod(TestObject value, int primitive, String valueString) {
        return "AIP";
    }

}

@Component
public class AIFStrategyPattern {

    public String executeMethod(TestObject value, int primitive, String valueString) {
        return "AIF";
    }

}
Interceptor to define which implementation to be used

@Aspect @Slf4j public class ProductCompanyBoundImplSelectionInterceptor implements MethodInterceptor {

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

Config

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
Test Classes @SpringBootTest class StrategyPatternAopApplicationTests {

@Autowired
private StrategyPattern baseStrategyPattern;

@Test
void whenDivisionAIP_returnAIPResult() {
    this.assertDivisionStategyIsOk("AIP");
}

@Test
void whenDivisionAIF_returnAIFResult() {
    this.assertDivisionStategyIsOk("AIF");
}

@Test
void whenDivisionAII_notFound_returnException() {
    Assertions.assertThrows(ProductCompanySelectionClassMissingException.class, () -> {
        this.assertDivisionStategyIsOk("AII");
    });

}

private void assertDivisionStategyIsOk(String division) {
    this.setupSecurityContext(division);
    String strategyResult =
            this.baseStrategyPattern.executeMethod(new TestObject("test"), 0, "TEST");
    assertThat(division).isEqualTo(strategyResult);

}

private void setupSecurityContext(String division) {
    SecurityContext context = SecurityContextHolder.getContext();
    UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken("oid", null);
    AuthenticationDetails authenticationDetails = new AuthenticationDetails(division);
    authentication.setDetails(authenticationDetails);
    context.setAuthentication(authentication);
    SecurityContextHolder.getContext().setAuthentication(authentication);
}
}

So what do we want: to replace StrategyPattern class with an Interface or Abstract Class. Now we use a default class which does nothing, which is ugly.

So any suggestions how we do this, because the annotations only work with methods which should be executed.

EDIT 22/10/2021 Changed code to work with Spring @Component auto-detection and ApplicationContext

For an MVCE, as suggested by kriegaex, clone following github repo: https://github.com/nvanhoeck/strategy-pattern-aop.git

The tests are successful, what we want is that StrategyPattern class becomes an interface and still make this work.
