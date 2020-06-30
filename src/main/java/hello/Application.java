package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;

@SpringBootApplication
@RestController
public class Application {

	@Value("${upstream:http://worldclockapi.com/api/json/utc/now}")
	private String upstream;

	@RequestMapping("/**")
	public String pass(HttpServletRequest request) {
		String path = request.getServletPath();
		ResponseEntity<String> response = requestUpstream(path);
		return response.getBody();
	}

	private ResponseEntity<String> requestUpstream(String path) {
		RestTemplate restTemplate = new RestTemplate();
		String resourceUrl = upstream + path;
		return restTemplate.getForEntity(resourceUrl, String.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}