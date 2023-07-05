package com.example.troknite.troknite.controller;

import com.example.troknite.troknite.model.ProductDTO;
import com.example.troknite.troknite.service.ProductService;
import jakarta.validation.Valid;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping(value = "/api/products", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:3000")

public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable final Long id) {
        return ResponseEntity.ok(productService.get(id));
    }

     
    
    
    @PostMapping
    public ResponseEntity<Long> createProduct(@ModelAttribute @Valid final ProductDTO productDTO) throws IOException {
    	List<MultipartFile> imageFiles = productDTO.getImageFiles();
    	
    	System.out.println(imageFiles);

        List<byte[]> images = imageFiles.stream()
                .map(t -> {
					try {
						return t.getBytes();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				})
                .collect(Collectors.toList());

        ProductDTO product = new ProductDTO();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setCategory(productDTO.getCategory());
        product.setValue(productDTO.getValue());
        product.setUsers(productDTO.getUsers());
        product.setImageFiles(productDTO.getImageFiles()); // Remove the imageFiles from the product object
        
        Long createdProduct = productService.create(product);
        productService.addProductImages(createdProduct, images); // Call a new method to add images to the product

        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable final Long id,
            @ModelAttribute @Valid final ProductDTO productDTO) throws IOException {
        List<MultipartFile> imageFiles = productDTO.getImageFiles();
        
        List<byte[]> images = imageFiles.stream()
                .map(t -> {
                    try {
                        return t.getBytes();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .collect(Collectors.toList());

        ProductDTO product = new ProductDTO();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setCategory(productDTO.getCategory());
        product.setValue(productDTO.getValue());
        product.setUsers(productDTO.getUsers());
        product.setImageFiles(productDTO.getImageFiles()); // Remove the imageFiles from the product object

        productService.update(id, product, images); // Call the update method in your service and pass the images

        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable final String category) {
        return ResponseEntity.ok(productService.findAllByCategory(category));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProductsByName(@RequestParam final String name) {
        return ResponseEntity.ok(productService.findAllByNameContaining(name));
    }

}
