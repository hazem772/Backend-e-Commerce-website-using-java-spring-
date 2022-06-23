package com.hazem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
 import com.hazem.DTO.ProductDto;
import com.hazem.exceptions.ProductNotExistsException;
import com.hazem.model.Category;
import com.hazem.model.Product;
import com.hazem.repository.ProductRepository;

@Service
public class ProductService {
	 @Autowired
	 ProductRepository productRepository;
	 
	  public void createProduct(ProductDto productDto, Category category) {
	        Product product = new Product();
	        product.setDescription(productDto.getDescription());
	        product.setImageURL(productDto.getImageURL());
	        product.setName(productDto.getName());
	        product.setCategory(category);
	        product.setPrice(productDto.getPrice());
	        productRepository.save(product);
	    }

	    public ProductDto getProductDto(Product product) {
	        ProductDto productDto = new ProductDto();
	        productDto.setDescription(product.getDescription());
	        productDto.setImageURL(product.getImageURL());
	        productDto.setName(product.getName());
	        productDto.setCategoryId(product.getCategory().getId());
	        productDto.setPrice(product.getPrice());
	        productDto.setId(product.getId());
	        return productDto;
	    }

	    public List<ProductDto> getAllProducts() {
	        List<Product> allProducts = productRepository.findAll();

	        List<ProductDto> productDtos = new ArrayList<>();
	        for(Product product: allProducts) {
	            productDtos.add(getProductDto(product));
	        }
	        return productDtos;
	    }

	    public void updateProduct(ProductDto productDto, Integer productId) throws Exception {
	        Optional<Product> optionalProduct = productRepository.findById(productId);
	        // throw an exception if product does not exists
	        if (!optionalProduct.isPresent()) {
	            throw new Exception("product not present");
	        }
	        Product product = optionalProduct.get();
	        product.setDescription(productDto.getDescription());
	        product.setImageURL(productDto.getImageURL());
	        product.setName(productDto.getName());
	        product.setPrice(productDto.getPrice());
	        productRepository.save(product);
	    }

	    public Product findById(Integer productId) throws ProductNotExistsException {
	        Optional<Product> optionalProduct = productRepository.findById(productId);
	        if (optionalProduct.isEmpty()) {
	            throw new ProductNotExistsException("product Does Not Exist: " + productId);
	        }
	        return optionalProduct.get();
	    }

}
