package com.stackoverflowmvce.strategypatternaop.annotations;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation placed upon a method from a "default" class.
 * This default class can be seen as an abstract class which returns default values.
 * This default class has multiple "siblings" or implementations, based on the product-company in the token.
 * These implementations extend the default class with all its methods and give it its own function-
 * ality, similar like the strategy pattern: https://sourcemaking.com/design_patterns/strategy
 *
 * EG:              TestClass
 *              /       |       \
 *   AIPTestClass  AIITestClass  AIFTestClass
 *
 * REQUIREMENTS:
 * 1) Have a default class with a base name, eg: TestClass
 * 2) For each possible implementation foresee an implementation, eg: AIPTestClass
 * 3) Add the annotation to the default methods which should have a product-company bound implementation
 * 4) Annotation should be placed in  @Component based classes (or service, controller, ...)
 *
 * Check ProductCompanyBoundImplSelectionInterceptor for how this is handled
 */
@Inherited
@Target({METHOD})
@Retention(RUNTIME)
public @interface ProductCompanyImplSelection {

}
