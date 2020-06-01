package com.tenniscourts;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ActiveProfiles("test-integration")
@RunWith(SpringRunner.class)
@SpringBootTest(
	webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(
	classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public abstract class TemplateIT {

	private ResourceReader reader;
	@Autowired
	private MockMvc mvc;

	@Before
	public final void setUp()
		throws Exception {
		reader = new ResourceReader();
	}

	public final ResourceReader reader() {
		return reader;
	}

	public final MockMvc mvc() {
		return mvc;
	}

	protected final ResultMatcher contentJSON(
		final String expectedJSON) {
		return MockMvcResultMatchers.content().json(expectedJSON, true);
	}
}
