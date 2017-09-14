/**
 * 
 */
package com.desire3d.examples.jdo.repository.impl;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.springframework.stereotype.Repository;

import com.desire3d.examples.jdo.model.Product;
import com.desire3d.examples.jdo.repository.ProductRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Mahesh Pardeshi
 *
 */

@Repository
public class ProductRepositoryImpl implements ProductRepository {

	private final PersistenceManagerFactory pmf;

	public ProductRepositoryImpl(PersistenceManagerFactory pmf) {
		super();
		this.pmf = pmf;
	}

	@Override
	public Mono<Void> save(Mono<Product> product) {
		PersistenceManager pm = pmf.getPersistenceManager();
		return product.doOnNext(productSub -> {
			pm.makePersistent(productSub);
		}).thenEmpty(Mono.empty());
	}

	@Override
	public Mono<Product> update(Product product) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Product oldProduct = pm.getObjectById(Product.class, product.getProductId());
		if (product.getCategory() != null)
			oldProduct.setCategory(product.getCategory());
		if (product.getName() != null)
			oldProduct.setName(product.getName());
		if (product.getDescription() != null)
			oldProduct.setDescription(product.getDescription());
		if (product.getPrice() != null)
			oldProduct.setPrice(product.getPrice());
		return Mono.just(pm.makePersistent(oldProduct));
	}

	@Override
	public void delete(Long productId) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Product product = pm.getObjectById(Product.class, productId);
		pm.deletePersistent(product);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Flux<Product> findAll() {
		PersistenceManager pm = pmf.getPersistenceManager();
		Query query = pm.newQuery(Product.class);
		return Flux.fromIterable((List<Product>) query.execute());
	}

	@Override
	public Mono<Product> findById(Long productId) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Product product = pm.getObjectById(Product.class, productId);
		return Mono.justOrEmpty(product);
	}
}
