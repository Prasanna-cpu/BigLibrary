package com.biglibrary.catalog_service.mapper;

import com.biglibrary.catalog_service.dto.ProductDTO;
import com.biglibrary.catalog_service.entity.Product;

public class ProductMapper {

	public static ProductDTO mapToProductDTO(Product product) {
		ProductDTO productDTO = new ProductDTO();

		productDTO.setId(product.getId());
		productDTO.setCode(product.getCode());
		productDTO.setName(product.getName());
		productDTO.setDescription(product.getDescription());
		productDTO.setImageUrl(product.getImageUrl());
		productDTO.setPrice(product.getPrice());

		return productDTO;

	}

	public static Product mapToProduct(ProductDTO productDTO) {
		Product product = new Product();

		if (!productDTO.getId().isEmpty()) {
			product.setId(productDTO.getId());
		}

		product.setCode(productDTO.getCode());
		product.setName(productDTO.getName());
		product.setDescription(productDTO.getDescription());
		product.setImageUrl(productDTO.getImageUrl());
		product.setPrice(productDTO.getPrice());

		return product;

	}

}
