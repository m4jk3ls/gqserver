package pl.edu.pk.geoquiz.gqserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.edu.pk.geoquiz.gqserver.api.RestCountries;

@SpringBootApplication
public class GqserverApplication {

	public static void main(String[] args) {
//		SpringApplication.run(GqserverApplication.class, args);
		RestCountries restCountries = new RestCountries();
		System.out.println(restCountries.getCountriesWithSpecificField("regionalBlocs|name", 4));
	}
}