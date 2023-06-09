package principal;

import java.text.ParseException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
	  

	public static void main(String[] args) throws ParseException{
    	 SpringApplication.run(App.class, args);


}
	
	/*protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(App.class);
		
		
	}*/
}