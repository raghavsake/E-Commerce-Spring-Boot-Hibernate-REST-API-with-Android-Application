package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ecommerce.model.Product;
import com.ecommerce.model.ProductApiResponse;
import com.ecommerce.service.FileStorageService;
import com.ecommerce.service.ProductsService;

@RestController
@RequestMapping("/products")
public class ProductsController {
	
	@Autowired
	ProductsService productsService;
	
	@Autowired
	FileStorageService fileStorageService;
	
	@GetMapping(value="/")
	public ResponseEntity<ProductApiResponse> getProductsByCategory(@RequestParam("userId") int userId,@RequestParam("category") String category){
		List<Product> p=productsService.getProductsByCategory(userId, category);
		return ResponseEntity.status(200).body(new ProductApiResponse(p));
		
	}
	@GetMapping(value="/search")
	public ResponseEntity<ProductApiResponse> searchProducts(@RequestParam("userId") int userId,@RequestParam("keyword") String keyword){
		List<Product> p=productsService.searchProduct(userId, keyword);
		return ResponseEntity.status(200).body(new ProductApiResponse(p));
		
	}
	@PostMapping(value = "/insert")
	public ResponseEntity<String> add(@RequestParam("name")String product_ame,
			@RequestParam("price")double price, @RequestParam("quantity")int quantity, 
			@RequestParam("supplier")String supplier, @RequestParam("category")String category,
			@RequestParam("file") MultipartFile file) {
		
		String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(fileName)
                .toUriString();
		try
		{
			productsService.insertProduct(product_ame, price, quantity, supplier, category, fileDownloadUri);
			return ResponseEntity.status(200).body("Your product is inserted");
	
		}
		catch(Exception e)
		{
			return ResponseEntity.status(500).body("Your product could not be inserted");
			
		}
	}

}
