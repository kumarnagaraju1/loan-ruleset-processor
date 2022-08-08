package com.tsb.sco.fl.rulesetprocessor;

import com.tsb.sco.fl.rulesetprocessor.service.RuleSetProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class LoanRuleSetProcessorApplication {

	@Autowired
	RuleSetProcessorService ruleSetProcessorService;

	public static void main(String[] args) {

		SpringApplication.run(LoanRuleSetProcessorApplication.class, args);
	}

	/*@PostConstruct
	public void init() {
		ruleSetProcessorService.test();
	}
*/
}
