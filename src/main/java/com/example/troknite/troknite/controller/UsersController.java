package com.example.troknite.troknite.controller;

import com.example.troknite.troknite.config.CustomMultipartFile;
import com.example.troknite.troknite.domain.Product;
import com.example.troknite.troknite.domain.Users;
import com.example.troknite.troknite.model.ProductDTO;
import com.example.troknite.troknite.model.UsersDTO;
import com.example.troknite.troknite.repos.ProductRepository;
import com.example.troknite.troknite.repos.UsersRepository;
import com.example.troknite.troknite.service.ProductService;
import com.example.troknite.troknite.service.UsersService;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping(value = "/api/userss", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:3000")

public class UsersController {

    private final UsersService usersService;

    public UsersController(final UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public ResponseEntity<List<UsersDTO>> getAllUserss() {
        return ResponseEntity.ok(usersService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersDTO> getUsers(@PathVariable final Long id) {
        return ResponseEntity.ok(usersService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createUsers(@RequestBody @Valid final UsersDTO usersDTO) {
        final Long createdId = usersService.create(usersDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUsers(@PathVariable final Long id,
            @RequestBody @Valid final UsersDTO usersDTO) {
        usersService.update(id, usersDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsers(@PathVariable final Long id) {
        usersService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ProductService productService;
    @GetMapping("/{userId}/products")
    public ResponseEntity<List<ProductDTO>> getAllProductsByUsers(@PathVariable(value = "userId") Long userId) {

    	Optional<Users> optionalUser = usersRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Users user = optionalUser.get();
        List<Product> products = productService.findAllByUser(user);

        if(products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<ProductDTO> productDTOS = new ArrayList<>();
        for(Product product : products) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setDescription(product.getDescription());
            productDTO.setCategory(product.getCategory());
            if (product.getImages() != null) {
                List<MultipartFile> multipartFiles = product.getImages().stream()
                        .map(bytes -> new CustomMultipartFile(bytes, "file"))
                        .collect(Collectors.toList());

                productDTO.setImageFiles(multipartFiles);
            }
            productDTO.setValue(product.getValue());
            productDTO.setAvailable(product.getAvailable());
            productDTO.setUsers(userId);
            productDTOS.add(productDTO);
            
        }
        return ResponseEntity.ok().body(productDTOS);    }
   

}
