package com.desire3d.examples.jdo.handler;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.desire3d.examples.jdo.model.Product;
import com.desire3d.examples.jdo.repository.ProductRepository;

import reactor.core.publisher.Mono;

/**
 * @author Mahesh Pardeshi
 *
 */
public class ProductHandler {

	private final Logger logger = LoggerFactory.getLogger(ProductHandler.class);

	private final ProductRepository repository;

	public ProductHandler(ProductRepository repository) {
		super();
		this.repository = repository;
	}

	public Mono<ServerResponse> save(ServerRequest request) {
		logger.info("save call recieved...");
		Mono<Product> product = request.bodyToMono(Product.class);
		return ServerResponse.ok().build(repository.save(product));
	}

	public Mono<ServerResponse> findAll(ServerRequest request) {
		logger.info("findAll call recieved...");
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(repository.findAll(), Product.class);
	}

	public Mono<ServerResponse> findById(ServerRequest request) {
		logger.info("findById call recieved...");
		Long id = Long.valueOf(request.pathVariable("id"));
		Mono<ServerResponse> notFound = ServerResponse.notFound().build();
		Mono<Product> productMono = repository.findById(id);
		return productMono.flatMap(product -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(fromObject(productMono)))
				.switchIfEmpty(notFound);
	}
}