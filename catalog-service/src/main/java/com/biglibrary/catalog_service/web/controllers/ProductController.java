package com.biglibrary.catalog_service.web.controllers;

import com.biglibrary.catalog_service.dto.ProductDTO;
import com.biglibrary.catalog_service.records.PagedResult;
import com.biglibrary.catalog_service.service.ProductService;
import com.biglibrary.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllProducts(@RequestParam(name = "page", defaultValue = "1") int pageNo,
			@RequestParam(name = "size", defaultValue = "10") int pageSize) {
		PagedResult<ProductDTO> pagedResult = productService.getAllProducts(pageNo, pageSize);
		return ResponseEntity.status(HttpStatus.OK).body(
				new ApiResponse(pagedResult, "Products retrieved successfully", HttpStatus.OK.value(), HttpStatus.OK));
	}

	@GetMapping("/code/{code}")
	public ResponseEntity<ApiResponse> getProductByCode(@PathVariable String code) {
		ProductDTO productDTO = productService.getProductByCode(code);
		return ResponseEntity.status(HttpStatus.OK).body(
				new ApiResponse(productDTO, "Product retrieved successfully", HttpStatus.OK.value(), HttpStatus.OK));
	}

}
