package principal;

import java.text.ParseException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class App {
	  

	public static void main(String[] args) throws ParseException{
		//Tablas t = new Tablas();
    	//t.crearTablas();
    	 SpringApplication.run(App.class, args);


}
	
	/*protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(App.class);
		
	}*/
}