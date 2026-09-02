package com.biglibrary.catalog_service.repository;

import com.biglibrary.catalog_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

	@Query("SELECT p FROM Product p WHERE p.code = :code")
	Optional<Product> findByCode(String code);
}
