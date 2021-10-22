package com.stackoverflowmvce.strategypatternaop.business;

import com.stackoverflowmvce.strategypatternaop.annotations.ProductCompanyImplSelection;
import com.stackoverflowmvce.strategypatternaop.model.TestObject;
import org.springframework.stereotype.Component;

@Component
public class AIFStrategyPattern {

    public String executeMethod(TestObject value, int primitive, String valueString) {
        return "AIF";
    }

}
