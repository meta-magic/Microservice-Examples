/**
 * 
 */
package com.desire3d.examples.jdo.router;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.desire3d.examples.jdo.handler.ProductHandler;
import com.desire3d.examples.jdo.repository.ProductRepository;

/**
 * @author Mahesh Pardeshi
 *
 */
@Configuration
public class ProductRouter {

	private final ProductRepository repository;

	public ProductRouter(ProductRepository repository) {
		super();
		this.repository = repository;
	}

	@Bean
	public RouterFunction<ServerResponse> routingFunction() {
		ProductHandler handler = new ProductHandler(repository);

		return nest(path("/ProductRoute"),
				nest(accept(APPLICATION_JSON), route(GET("/findById/{id}"), handler::findById).andRoute(GET("/findAll"), handler::findAll))
						.andRoute(POST("/save").and(contentType(APPLICATION_JSON)), handler::save));
	}
}
