package com.tenniscourts.guests;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.tenniscourts.TemplateIT;


public class GuestControllerITTest
	extends
	TemplateIT {

	@Test
	public void requestPOST() throws Exception {
		String requestPOST = readContent("IT/guest/POST/request-POST-01.json");
		String expectedPOSTResponse = readContent("IT/guest/POST/request-POST-01-response.json");
		mvc().perform(
			MockMvcRequestBuilders.post("/guest")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestPOST)
		).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(contentJSON(expectedPOSTResponse));
	}

	@Test
	public void requestGETAll() throws Exception {
		String requestPOST = readContent("IT/guest/POST/request-POST-02.json");
		String expectedPOSTResponse = readContent("IT/guest/POST/request-POST-02-response.json");
		String expectedResponse = readContent("IT/guest/GET/request-GET-all-response.json");
		mvc().perform(
			MockMvcRequestBuilders.post("/guest")
			.contentType(MediaType.APPLICATION_JSON)
			.content(requestPOST)
		).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(contentJSON(expectedPOSTResponse));

		mvc().perform(
			MockMvcRequestBuilders.get("/guest")
		).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(contentJSON(expectedResponse));
	}

	@Test
	public void requestGETOne() throws Exception {
		String requestPOST = readContent("IT/guest/POST/request-POST-03.json");
		String expectedPOSTResponse = readContent("IT/guest/POST/request-POST-03-response.json");
		String expectedResponse = readContent("IT/guest/GET/request-GET-one-response.json");

		mvc().perform(
			MockMvcRequestBuilders.post("/guest")
			.contentType(MediaType.APPLICATION_JSON)
			.content(requestPOST)
		).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(contentJSON(expectedPOSTResponse));

		mvc().perform(
			MockMvcRequestBuilders.get("/guest/3")
		).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(contentJSON(expectedResponse));
	}

	@Test
	public void requestGETbyName() throws Exception {
		String expectedResponse = readContent("IT/guest/GET/request-GET-one-byname-response.json");
		mvc().perform(
			MockMvcRequestBuilders.get("/guest?name=Rafael Nadal")
			.contentType(MediaType.APPLICATION_JSON)
		).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(contentJSON(expectedResponse));
	}

	@Test
	public void requestUPDATE() throws Exception {
		String requestPOST = readContent("IT/guest/POST/request-POST-04.json");
		String expectedPOSTResponse = readContent("IT/guest/POST/request-POST-04-response.json");
		String requestPUT = readContent("IT/guest/PUT/request-PUT-01.json");
		String expectedResponse = readContent("IT/guest/PUT/request-PUT-01-response.json");

		mvc().perform(
			MockMvcRequestBuilders.post("/guest")
			.contentType(MediaType.APPLICATION_JSON)
			.content(requestPOST)
		).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(contentJSON(expectedPOSTResponse));

		mvc().perform(
			MockMvcRequestBuilders.put("/guest/3")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestPUT))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(contentJSON(expectedResponse));
	}

	@Test
	public void requestDELETE() throws Exception {
		String requestPOST = readContent("IT/guest/POST/request-POST-05.json");
		String expectedPOSTResponse = readContent("IT/guest/POST/request-POST-05-response.json");
		String expectedGETResponse = readContent("IT/guest/GET/request-GET-one-not-found-01.json");

		mvc().perform(
			MockMvcRequestBuilders.post("/guest")
			.contentType(MediaType.APPLICATION_JSON)
			.content(requestPOST)
		).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(contentJSON(expectedPOSTResponse));

		mvc().perform(
			MockMvcRequestBuilders.delete("/guest/3")
		).andExpect(MockMvcResultMatchers.status().isNoContent())
		.andExpect(MockMvcResultMatchers.content().string(""));

		mvc().perform(
			MockMvcRequestBuilders.get("/guest/3")
			.contentType(MediaType.APPLICATION_JSON)
			.content(requestPOST)
		).andExpect(MockMvcResultMatchers.status().isNotFound())
		.andExpect(contentJSONLenient(expectedGETResponse));

	}
}
