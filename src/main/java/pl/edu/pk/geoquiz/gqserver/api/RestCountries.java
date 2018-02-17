package pl.edu.pk.geoquiz.gqserver.api;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RestCountries {

	private static final RestTemplate REST_TEMPLATE;
	private static final HttpEntity<String> ENTITY;
	private static final String MAIN_URL = "https://restcountries.eu/rest/v2";

	static {
		REST_TEMPLATE = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		ENTITY = new HttpEntity<>("parameters", headers);
	}

	public RestCountries() {
	}

	public List<Map<String, Object>> getData(String field) {
		String url = MAIN_URL + "/all?fields=name;" + field;
		List<Map<String, Object>> apiResult = REST_TEMPLATE.exchange(url, HttpMethod.GET, ENTITY, new ParameterizedTypeReference<List<Map<String, Object>>>() {
		}).getBody();
		return apiResult;
	}
}