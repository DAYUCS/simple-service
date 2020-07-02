package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

@SpringBootApplication
@RestController
public class Application {

	@Value("${upstream:http://worldclockapi.com/api/json/utc/now}")
	private String upstream;

	@RequestMapping("/**")
	public String pass(HttpServletRequest request, @RequestHeader HttpHeaders requestHeaders) {
		String path = request.getServletPath();
		ResponseEntity<String> response = requestUpstream(path, requestHeaders);
		return response.getBody();
	}

	private ResponseEntity<String> requestUpstream(String path, HttpHeaders requestHeaders) {
		final HttpEntity<String> entity = new HttpEntity<String>(requestHeaders);
		RestTemplate restTemplate = new RestTemplate();
		String resourceUrl = upstream + path;
		return restTemplate.exchange(resourceUrl, HttpMethod.GET, entity, String.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}