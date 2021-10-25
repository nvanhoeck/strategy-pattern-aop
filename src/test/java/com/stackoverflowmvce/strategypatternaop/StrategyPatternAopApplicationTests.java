package com.stackoverflowmvce.strategypatternaop;

import com.stackoverflowmvce.strategypatternaop.business.DefaultStrategyPattern;
import com.stackoverflowmvce.strategypatternaop.exceptions.ProductCompanySelectionClassMissingException;
import com.stackoverflowmvce.strategypatternaop.model.AuthenticationDetails;
import com.stackoverflowmvce.strategypatternaop.model.TestObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StrategyPatternAopApplicationTests {

    @Autowired
    private DefaultStrategyPattern defaultStrategyPattern;

    @Test
    void whenDivisionAIP_returnAIPResult() {
        assertDivisionStategyIsOk("AIP");
    }

    @Test
    void whenDivisionAIF_returnAIFResult() {
        assertDivisionStategyIsOk("AIF");
    }

    @Test
    void whenDivisionAII_notFound_returnException() {
        Assertions.assertThrows(
            ProductCompanySelectionClassMissingException.class,
            () -> assertDivisionStategyIsOk("AII")
        );
    }

    private void assertDivisionStategyIsOk(String division) {
        this.setupSecurityContext(division);
        String strategyResult = defaultStrategyPattern.executeMethod(new TestObject("test"), 0, "TEST");
        assertThat(division).isEqualTo(strategyResult);
    }

    private void setupSecurityContext(String division) {
        SecurityContext context = SecurityContextHolder.getContext();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("oid", null);
        AuthenticationDetails authenticationDetails = new AuthenticationDetails(division);
        authentication.setDetails(authenticationDetails);
        context.setAuthentication(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
