package com.api.automation.karateframework.configuration.getrequest;

import com.intuit.karate.junit5.Karate;
import com.intuit.karate.junit5.Karate.Test;

public class TestGetRequest {

	@Test
	public Karate runTest() {
		return Karate.run("getRequest").relativeTo(getClass());

	}

}


