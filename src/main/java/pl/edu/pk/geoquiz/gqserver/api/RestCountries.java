package pl.edu.pk.geoquiz.gqserver.api;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

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

	public List<Map<String, String>> getCountriesWithSpecificField(String field, int numberOfCountries) {
		String secondField = null;
		if (field.contains("|")) {
			secondField = field.substring(field.indexOf("|") + 1);
			field = field.substring(0, field.indexOf("|"));
		}
		String url = MAIN_URL + "/all?fields=name;" + field;
		List<Map<String, Object>> allCountries = REST_TEMPLATE.exchange(url, HttpMethod.GET, ENTITY, new ParameterizedTypeReference<List<Map<String, Object>>>() {
		}).getBody();

		List<Map<String, String>> randomCountries = new ArrayList<>();
		do {
			Map<String, Object> firstCountry = allCountries.get(new Random().nextInt(allCountries.size()));
			Map<String, String> mapToInsert = new HashMap<>();
			mapToInsert.put("name", (String) firstCountry.get("name"));

			if (firstCountry.get(field) instanceof List && !((List) firstCountry.get(field)).isEmpty() && firstCountry.get(field) != null && secondField != null) {
				List<Map<String, String>> firstCountryFieldHowList = (List<Map<String, String>>) firstCountry.get(field);
				Map<String, String> randomMap = firstCountryFieldHowList.get(new Random().nextInt(firstCountryFieldHowList.size()));
				mapToInsert.put(field, randomMap.get(secondField));
				randomCountries.add(mapToInsert);
			} else if (!(firstCountry.get(field) instanceof List) && firstCountry.get(field) != null && secondField == null) {
				mapToInsert.put(field, (String) firstCountry.get(field));
				randomCountries.add(mapToInsert);
			}
		} while (randomCountries.isEmpty());

		/*Teraz trzeba bedzie losowac randomowy kraj, nastepnie sprawdzac, czy jego nazwa nie duplikuje sie z nazwa juz istniejaca w liscie randomCountries.
		 * Jesli warunek zostanie spelniony, to kolejnym krokiem bedzie sprawdzenie kazdego np. jezyka nowo wylosowanego panstwa, czy aby nie duplikuje sie
		 * z juz isniejacym w randomCountries. Jesli drugi z wymienionych warunkow jest spelniony, to wylosowane panstwo wraz np. ze swoim jezykem nadaje
		 * sie do wstawienia do listy randomCountries. Jesli proba znalezienia takiego panstwa zakonczy sie fiaskiem, to nie robic nic i wylosowac nowe
		 * panstwo (analogicznie do powyzszej konstrukcji, ktora szuka pierwszego panstwa do listy randomCountries).*/

		return randomCountries;
	}
}