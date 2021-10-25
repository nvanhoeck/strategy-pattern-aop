package com.stackoverflowmvce.strategypatternaop.business;

import com.stackoverflowmvce.strategypatternaop.model.TestObject;
import org.springframework.stereotype.Component;

@Component
public class AIPStrategyPattern extends DefaultStrategyPattern {
    @Override
    public String executeMethod(TestObject value, int primitive, String valueString) {
        return "AIP";
    }
}
