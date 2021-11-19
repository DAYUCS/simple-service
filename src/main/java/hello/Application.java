package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import io.opencensus.trace.AttributeValue;
import io.opencensus.common.Scope;
import io.opencensus.trace.Span;
import io.opencensus.trace.Status;
import io.opencensus.exporter.trace.jaeger.*;
import io.opencensus.trace.Tracer;
import io.opencensus.trace.Tracing;
import io.opencensus.trace.config.TraceConfig;
import io.opencensus.trace.config.TraceParams;
import io.opencensus.trace.samplers.Samplers;

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
	public String pass(HttpServletRequest request) {
		String path = request.getServletPath();
		ResponseEntity<String> response = requestUpstream(path);
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