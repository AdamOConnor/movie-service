package com.api.automation.karateframework.configuration.putrequest;

import com.intuit.karate.junit5.Karate;
import com.intuit.karate.junit5.Karate.Test;

public class TestPutRunner {

	@Test
	public Karate runTest() {
		return Karate.run("updateMovieEntry").relativeTo(getClass());

	}
}


