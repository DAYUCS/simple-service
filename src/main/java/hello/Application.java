package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.extension.annotations.WithSpan;

import javax.servlet.http.HttpServletRequest;

@SpringBootApplication
@RestController
public class Application {

	@Value("${upstream:http://worldclockapi.com/api/json/utc/now}")
	private String upstream;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@RequestMapping("/**")
	@WithSpan
	public String pass(HttpServletRequest request) {
		String path = request.getServletPath();
		Span span = Span.current();
		span.setAttribute("request.path", path);
		span.addEvent("app.request.out");
		ResponseEntity<String> response = requestUpstream(path);
		span.addEvent("app.request.return");
		return response.getBody();
	}

	private ResponseEntity<String> requestUpstream(String path) {
		String resourceUrl = upstream + path;
		return restTemplate().getForEntity(resourceUrl, String.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}