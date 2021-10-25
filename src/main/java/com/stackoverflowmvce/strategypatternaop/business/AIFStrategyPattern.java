package com.stackoverflowmvce.strategypatternaop.business;

import com.stackoverflowmvce.strategypatternaop.model.TestObject;
import org.springframework.stereotype.Component;

@Component
public class AIFStrategyPattern implements StrategyPattern {
    @Override
    public String executeMethod(TestObject value, int primitive, String valueString) {
        return "AIF";
    }
}
