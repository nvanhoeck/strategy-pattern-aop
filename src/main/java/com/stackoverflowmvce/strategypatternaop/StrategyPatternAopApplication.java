package com.stackoverflowmvce.strategypatternaop;

import com.stackoverflowmvce.strategypatternaop.config.SpringSecurityAOPConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({SpringSecurityAOPConfig.class})
public class StrategyPatternAopApplication {

	public static void main(String[] args) {
		SpringApplication.run(StrategyPatternAopApplication.class, args);
	}

}
