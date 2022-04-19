package com.api.automation.karateframework.configuration;


import org.junit.jupiter.api.Test;
import com.intuit.karate.Runner.Builder;

class ParallelBuilder {

	@Test
	public void executeKarateTest() {
		Builder aRunner = new Builder();
		aRunner.path("classpath:com/api/automation");
		aRunner.parallel(5);
	}

}
