package com.stackoverflowmvce.strategypatternaop.business;

import com.stackoverflowmvce.strategypatternaop.annotations.ProductCompanyImplSelection;
import com.stackoverflowmvce.strategypatternaop.model.TestObject;

public interface StrategyPattern {
    @ProductCompanyImplSelection
    String executeMethod(TestObject value, int primitive, String valueString);
}
