package com.stackoverflowmvce.strategypatternaop.business;

import com.stackoverflowmvce.strategypatternaop.annotations.ProductCompanyDefaultImpl;
import com.stackoverflowmvce.strategypatternaop.annotations.ProductCompanyImplSelection;
import com.stackoverflowmvce.strategypatternaop.model.TestObject;
import org.springframework.stereotype.Component;

@Component
@ProductCompanyDefaultImpl
public class DefaultStrategyPattern {
    @ProductCompanyImplSelection
    public String executeMethod(TestObject value, int primitive, String valueString) {
        return null;
    }
}
