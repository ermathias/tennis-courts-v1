package com.tenniscourts.schedules;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.tenniscourts.ResourceReader;
import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtRepository;


@ActiveProfiles("test-integration")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ScheduleControllerITTest {

	private ResourceReader reader;
	@Autowired
	private MockMvc mvc;
	@Autowired
	private TennisCourtRepository tennisCourtRepository;


	@Before
	public void setUp()
		throws Exception {
		reader = new ResourceReader();
	}

	@Test
	public void schedule() throws Exception {
		//ARRANGE DB
		save("tennis court 01");

		//ARRANGE DATA
		String request = reader.read("IT/schedule/post/example01.json");

		//ACT & ASSERT
		mvc.perform(
			MockMvcRequestBuilders.post("/schedule")
			.contentType(MediaType.APPLICATION_JSON)
			.content(request)
		)
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.content().string(""))
		.andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/schedule/2"))
		.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void scheduleBatch() throws Exception {
		//ARRANGE DB
		save("tennis court 01");
		save("tennis court 02");

		//ARRANGE DATA
		String request = reader.read("IT/schedule/batch/post/example01.json");
		String expectedResponse = reader.read("IT/schedule/batch/post/example01-response.json");

		//ACT & ASSERT
		mvc.perform(
			MockMvcRequestBuilders.post("/schedule/batch")
			.contentType(MediaType.APPLICATION_JSON)
			.content(request)
		)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(expectedResponse, true))
		.andDo(MockMvcResultHandlers.print());
	}

	private void save(final String name) {
		TennisCourt tennisCourt = new TennisCourt();
		tennisCourt.setName(name);
		tennisCourtRepository.save(tennisCourt);
	}
}
