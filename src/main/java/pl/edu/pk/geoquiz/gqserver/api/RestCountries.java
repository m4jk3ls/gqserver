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
		List<String> answersForCheck = new ArrayList<>();
		do {
			Map<String, Object> firstCountry = allCountries.get(new Random().nextInt(allCountries.size()));
			Map<String, String> mapToInsert = new HashMap<>();
			mapToInsert.put("name", firstCountry.get("name").toString());

			if (isFieldListOfMapsWithContent(firstCountry, field) && secondField != null) {
				List<Map<String, String>> firstCountryFieldHowMapList = (List<Map<String, String>>) firstCountry.get(field);
				for (Map<String, String> curr : firstCountryFieldHowMapList) {
					answersForCheck.add(curr.get(secondField));
				}
				Map<String, String> randomMap = firstCountryFieldHowMapList.get(new Random().nextInt(firstCountryFieldHowMapList.size()));
				mapToInsert.put(field, randomMap.get(secondField));
				randomCountries.add(mapToInsert);
			} else if (isFieldListOfStringsWithContent(firstCountry, field) && secondField == null) {
				List<String> firstCountryFieldHowStringList = (List<String>) firstCountry.get(field);
				answersForCheck.addAll(firstCountryFieldHowStringList);
				mapToInsert.put(field, firstCountryFieldHowStringList.get(new Random().nextInt(firstCountryFieldHowStringList.size())));
				randomCountries.add(mapToInsert);
			} else if (isntFieldList(firstCountry, field) && secondField == null) {
				mapToInsert.put(field, firstCountry.get(field).toString());
				randomCountries.add(mapToInsert);
			}
		} while (randomCountries.isEmpty());

		while (randomCountries.size() < numberOfCountries) {
			Map<String, Object> anotherCountry = allCountries.get(new Random().nextInt(allCountries.size()));
			if (isFieldListOfMapsWithContent(anotherCountry, field) && secondField != null) {
				String potentialCandidate = null;
				List<Map<String, String>> anotherCountryFieldHowList = (List<Map<String, String>>) anotherCountry.get(field);
				for (Map<String, String> newField : anotherCountryFieldHowList) {
					boolean isValid = true;
					for (String curr : answersForCheck) {
						if (newField.get(secondField).equals(curr)) {
							isValid = false;
						}
					}
					if (isValid) {
						potentialCandidate = newField.get(secondField);
						break;
					}
				}
				if (potentialCandidate != null) {
					boolean uniqueCountryName = true;
					for (Map<String, String> currCountry : randomCountries) {
						if (anotherCountry.get("name").equals(currCountry.get("name"))) {
							uniqueCountryName = false;
						}
					}
					if (uniqueCountryName) {
						save(answersForCheck, potentialCandidate, anotherCountry.get("name").toString(), field, randomCountries);
					}
				}
			} else if (isFieldListOfStringsWithContent(anotherCountry, field) && secondField == null) {
				boolean uniqueCountryName = true;
				for (Map<String, String> currCountry : randomCountries) {
					if (anotherCountry.get("name").equals(currCountry.get("name"))) {
						uniqueCountryName = false;
					}
				}
				if (uniqueCountryName) {
					List<String> anotherCountryFieldHowStringList = (List<String>) anotherCountry.get(field);
					for (String curr1 : anotherCountryFieldHowStringList) {
						boolean isValid = true;
						for (String curr2 : answersForCheck) {
							if (curr1.equals(curr2)) {
								isValid = false;
							}
						}
						if (isValid) {
							save(answersForCheck, curr1, anotherCountry.get("name").toString(), field, randomCountries);
						}
					}
				}
			} else if (isntFieldList(anotherCountry, field) && secondField == null) {
				Map<String, String> potentialMapToInsert = new HashMap<>();
				potentialMapToInsert.put("name", anotherCountry.get("name").toString());
				potentialMapToInsert.put(field, anotherCountry.get(field).toString());
				boolean countryIsValid = true;
				for (Map<String, String> currCountry : randomCountries) {
					if (currCountry.get("name").equals(potentialMapToInsert.get("name")) ||
							currCountry.get(field).equals(potentialMapToInsert.get(field))) {
						countryIsValid = false;
					}
				}
				if (countryIsValid) {
					randomCountries.add(potentialMapToInsert);
				}
			}
		}
		return randomCountries;
	}

	private void save(List<String> answersForCheck, String answerToSave, String countryName,
					  String field, List<Map<String, String>> countryListForClient) {
		answersForCheck.add(answerToSave);
		Map<String, String> mapToInsert = new HashMap<>();
		mapToInsert.put("name", countryName);
		mapToInsert.put(field, answerToSave);
		countryListForClient.add(mapToInsert);
	}

	private boolean isFieldListOfMapsWithContent(Map<String, Object> map, String key) {
		return (map.get(key) instanceof List && !((List) map.get(key)).isEmpty() &&
				map.get(key) != null && ((List) map.get(key)).get(0) instanceof Map);
	}

	private boolean isFieldListOfStringsWithContent(Map<String, Object> map, String key) {
		return (map.get(key) instanceof List && !((List) map.get(key)).isEmpty() &&
				map.get(key) != null && ((List) map.get(key)).get(0) instanceof String);
	}

	private boolean isntFieldList(Map<String, Object> map, String key) {
		return (!(map.get(key) instanceof List) && map.get(key) != null);
	}
}