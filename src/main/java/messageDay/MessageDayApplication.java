package messageDay;

import messageDay.controllers.IndexController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MessageDayApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MessageDayApplication.class, args);
	}

}
