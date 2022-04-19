package com.api.automation.karateframework.configuration.postrequest;

import com.intuit.karate.junit5.Karate;
import com.intuit.karate.junit5.Karate.Test;

public class TestJavaMovieEntry {

	@Test
	public Karate runTest() {
		return Karate.run("createMovieEntry").relativeTo(getClass());

	}
}


