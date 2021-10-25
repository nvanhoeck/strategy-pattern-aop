package com.stackoverflowmvce.strategypatternaop.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Marks interface or base class methods to which a product-company-based strategy pattern should be applied, whenever
 * such methods from default classes annotated with {@link ProductCompanyDefaultImpl} are executed.
 */
@Target({ METHOD })
@Retention(RUNTIME)
public @interface ProductCompanyImplSelection {}
