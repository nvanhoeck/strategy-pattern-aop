package com.stackoverflowmvce.strategypatternaop.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Marks default implementations of interfaces or base classes containing methods annotated with
 * {@link ProductCompanyImplSelection}. This has the effect that instead of the default methods the corresponding
 * product company methods will be executed.
 * <p>
 * <b>Please note:</b> Classes marked with this annotation must have names starting with "Default", "Base" or
 * "Standard" prefix, followed by the general class/bean name. When resolving the product company strategy class/bean
 * name, the strategy bean will be generated by replacing the prefix by the product company name. E.g.,
 * <tt>DefaultSomething</tt> with product company name "Foo" would become <tt>FooSomething</tt>,
 * <tt>BaseSomethingElse</tt> with product company name "ACME" would become <tt>ACMESomethingElse</tt>,
 */
@Target({ TYPE })
@Retention(RUNTIME)
public @interface ProductCompanyDefaultImpl {}