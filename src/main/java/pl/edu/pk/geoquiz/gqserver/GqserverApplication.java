package pl.edu.pk.geoquiz.gqserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.edu.pk.geoquiz.gqserver.api.RestCountries;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class GqserverApplication {

	public static void main(String[] args) {
//		SpringApplication.run(GqserverApplication.class, args);
		RestCountries restCountries = new RestCountries();
		int number = 5;
		List<Map<String, String>> list;
		list = restCountries.getCountriesWithSpecificField("population", number);
		System.out.println(list);
		System.out.println(list.size());
		for (Map<String, String> curr : list) {
			System.out.println(curr);
		}
		for (Map<String, String> curr1 : list) {
			String tmp = curr1.get("population");
			int counter = 0;
			for (Map<String, String> curr2 : list) {
				if (tmp.equals(curr2.get("population"))) {
					counter++;
				}
			}
			if (counter > 1) {
				System.out.println("HUJOWO!!!");
			}
		}
	}
}