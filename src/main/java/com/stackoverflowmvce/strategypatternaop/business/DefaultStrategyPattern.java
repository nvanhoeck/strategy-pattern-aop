package com.stackoverflowmvce.strategypatternaop.business;

import com.stackoverflowmvce.strategypatternaop.annotations.ProductCompanyDefaultImpl;
import com.stackoverflowmvce.strategypatternaop.model.TestObject;
import org.springframework.stereotype.Component;

@Component
@ProductCompanyDefaultImpl
public class DefaultStrategyPattern implements StrategyPattern {
    @Override
    public String executeMethod(TestObject value, int primitive, String valueString) {
        return null;
    }
}
