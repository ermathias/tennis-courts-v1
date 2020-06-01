package com.tenniscourts.schedules;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.tenniscourts.TemplateIT;
import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtRepository;


public class ScheduleControllerITTest
	extends
	TemplateIT {

	@Autowired
	private TennisCourtRepository tennisCourtRepository;

	@Test
	public void schedule() throws Exception {
		//ARRANGE DB
		save("tennis court 01");

		//ARRANGE DATA
		String request = reader().read("IT/schedule/post/example01.json");

		//ACT & ASSERT
		mvc().perform(
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
		String request = reader().read("IT/schedule/batch/post/example01.json");
		String expectedResponse = reader().read("IT/schedule/batch/post/example01-response.json");

		//ACT & ASSERT
		mvc().perform(
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
