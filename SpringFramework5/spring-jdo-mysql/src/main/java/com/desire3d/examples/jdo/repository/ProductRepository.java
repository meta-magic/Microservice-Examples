/**
 * 
 */
package com.desire3d.examples.jdo.repository;

import com.desire3d.examples.jdo.model.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Mahesh Pardeshi
 *
 */
public interface ProductRepository {

	public Mono<Void> save(Mono<Product> product);

	public Mono<Product> update(Product product);

	public void delete(Long productId);

	public Flux<Product> findAll();

	public Mono<Product> findById(Long productId);
}
