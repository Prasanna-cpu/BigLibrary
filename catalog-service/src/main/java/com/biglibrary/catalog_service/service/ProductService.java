package com.biglibrary.catalog_service.service;

import com.biglibrary.catalog_service.dto.ProductDTO;
import com.biglibrary.catalog_service.entity.Product;
import com.biglibrary.catalog_service.mapper.ProductMapper;
import com.biglibrary.catalog_service.records.PagedResult;
import com.biglibrary.catalog_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductService {

	private final ProductRepository productRepository;

	public PagedResult<ProductDTO> getAllProducts(int pageNo, int pageSize) {
		Sort sort = Sort.by("name").ascending();
		pageNo = pageNo <= 1 ? 0 : pageNo - 1;
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		var productPage = productRepository.findAll(pageable);
		PagedResult<ProductDTO> pagedResult = new PagedResult<>(
				productPage.getContent().stream().map(ProductMapper::mapToProductDTO).toList(),
				(int) productPage.getTotalElements(), productPage.getNumber() + 1, productPage.getTotalPages(),
				productPage.isFirst(), productPage.isLast(), productPage.hasNext(), productPage.hasPrevious());

		return pagedResult;

	}

	public ProductDTO getProductByCode(String code) {
		Product product = productRepository.findByCode(code)
				.orElseThrow(() -> new RuntimeException("Product not found"));
		return ProductMapper.mapToProductDTO(product);
	}

}
