package com.tenniscourts.guests;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GuestIT {
	
	private final static String BASE_URL = "http://localhost:";
	
	private final static String TEST_NAME = "Serena Williams";
	private static final String UPDATE_TEST_NAME = "Venus Williams";

    @LocalServerPort
    private int port;
    
    @Autowired
	protected TestRestTemplate testRestTemplate;

    
    @Before
    public void setup() {
    	createGuest(TEST_NAME);
    }
    
    @After
    public void cleanup() {
    	removeGuest(findGuestByName(TEST_NAME).getBody().getId());
    }
    
	
    @Test
	public void createGuestTest() {
		ResponseEntity<GuestDTO> response = findGuestByName(TEST_NAME);
		
		Assert.assertTrue(response.getStatusCode() == HttpStatus.OK);
		Assert.assertTrue(response.getBody().getName().equals(TEST_NAME));
	}

    @Test
    public void createGuestNullNameTest() {
    	ResponseEntity<GuestDTO> response = createGuest(null);
    	
    	Assert.assertTrue(response.getStatusCode() == HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createGuestBlankNameTest() {
    	ResponseEntity<GuestDTO> response = createGuest("");
    	
    	Assert.assertTrue(response.getStatusCode() == HttpStatus.BAD_REQUEST);
    }

	@Test
	public void findGuestByIdTest() {
		GuestDTO guest = findGuestByName(TEST_NAME).getBody();
		String url = buildBaseGuestsURL() + guest.getId();
		HttpMethod method = HttpMethod.GET;
		HttpEntity<Void> entity = new HttpEntity<>(new HttpHeaders());

		ResponseEntity<GuestDTO> response = testRestTemplate.exchange(url, method, entity, GuestDTO.class);
		
		Assert.assertTrue(response.getStatusCode() == HttpStatus.OK);
		Assert.assertTrue(response.getBody().getName().equals(TEST_NAME));
	}

	@Test
	public void findGuestByInvalidIdTest() {
		String url = buildBaseGuestsURL() + -1;
		HttpMethod method = HttpMethod.GET;
		HttpEntity<Void> entity = new HttpEntity<>(new HttpHeaders());
		
		ResponseEntity<GuestDTO> response = testRestTemplate.exchange(url, method, entity, GuestDTO.class);
		
		Assert.assertTrue(response.getStatusCode() == HttpStatus.NOT_FOUND);
	}

	@Test
	public void findGuestByNameTest() {
		ResponseEntity<GuestDTO> response = findGuestByName(TEST_NAME);
		
		Assert.assertTrue(response.getStatusCode() == HttpStatus.OK);
		Assert.assertTrue(response.getBody().getName().equals(TEST_NAME));
	}
	
	@Test
	public void updateGuestTest() {
		GuestDTO guest = findGuestByName(TEST_NAME).getBody();
		String newGuestName = UPDATE_TEST_NAME;
		String url = buildBaseGuestsURL() + guest.getId() + "?name=" + newGuestName;
		HttpMethod method = HttpMethod.PUT;
		HttpEntity<Void> entity = new HttpEntity<>(new HttpHeaders());
		
		ResponseEntity<GuestDTO> response = testRestTemplate.exchange(url, method, entity, GuestDTO.class);
		
		Assert.assertTrue(response.getBody().getName().equals(newGuestName));
		Assert.assertTrue(response.getStatusCode() == HttpStatus.OK);
		
		removeGuest(guest.getId());
	}

	@Test
	public void updateGuestInvalidIdTest() {
		String newGuestName = UPDATE_TEST_NAME;
		String url = buildBaseGuestsURL() + "-1?name=" + newGuestName;
		HttpMethod method = HttpMethod.PUT;
		HttpEntity<Void> entity = new HttpEntity<>(new HttpHeaders());
		
		ResponseEntity<GuestDTO> response = testRestTemplate.exchange(url, method, entity, GuestDTO.class);
		
		Assert.assertTrue(response.getStatusCode() == HttpStatus.NOT_FOUND);
		Assert.assertTrue(findGuestByName(newGuestName).getStatusCode() == HttpStatus.NOT_FOUND);
	}

	@Test
	public void removeGuestTest() {
		GuestDTO guest = findGuestByName(TEST_NAME).getBody();
		
		ResponseEntity<Void> response = removeGuest(guest.getId());

		Assert.assertTrue(response.getStatusCode() == HttpStatus.OK);
		Assert.assertTrue(findGuestByName(TEST_NAME).getStatusCode() == HttpStatus.NOT_FOUND);
	}

	@Test
	public void removeGuestInvalidIdTest() {
		ResponseEntity<Void> response = removeGuest(-1L);
		
		Assert.assertTrue(response.getStatusCode() == HttpStatus.NOT_FOUND);
		Assert.assertTrue(findGuestByName(TEST_NAME).getStatusCode() == HttpStatus.OK);
	}

	@Test
    public void findAllTest() {
		String url = buildBaseGuestsURL();
		HttpMethod method = HttpMethod.GET;
		HttpEntity<Void> entity = new HttpEntity<>(new HttpHeaders());
		
		ResponseEntity<List<GuestDTO>> response = testRestTemplate.exchange(url, method, entity, new ParameterizedTypeReference<List<GuestDTO>>(){});

		Assert.assertTrue(response.getStatusCode() == HttpStatus.OK);
		Assert.assertTrue(response.getBody().stream().anyMatch(g -> g.getName().equals(TEST_NAME)));
    }
	
	private String buildBaseGuestsURL() {
		return BASE_URL + port + "/guests/";
	}

	private ResponseEntity<GuestDTO> createGuest(String name) {
		String url = buildBaseGuestsURL();
		HttpMethod method = HttpMethod.POST;
		
		CreateGuestRequestDTO guest = CreateGuestRequestDTO.builder()
														   .name(name)
														   .build();
		
		HttpEntity<CreateGuestRequestDTO> entity = new HttpEntity<>(guest, new HttpHeaders());
		
		return testRestTemplate.exchange(url, method, entity, GuestDTO.class);
	}
	
	private ResponseEntity<GuestDTO> findGuestByName(String name) {
		String url = buildBaseGuestsURL() + "name/" + name;
		HttpMethod method = HttpMethod.GET;
		HttpEntity<Void> entity = new HttpEntity<>(new HttpHeaders());
		
		return testRestTemplate.exchange(url, method, entity, GuestDTO.class);
	}
	
	private ResponseEntity<Void> removeGuest(Long id) {
		String url = buildBaseGuestsURL() + id;
		HttpMethod method = HttpMethod.DELETE;
		HttpEntity<Void> entity = new HttpEntity<>(new HttpHeaders());
		
		return testRestTemplate.exchange(url, method, entity, Void.class);
	}
}