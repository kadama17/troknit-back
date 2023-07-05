package com.example.troknite.troknite.service;

import com.example.troknite.troknite.config.CustomMultipartFile;
import com.example.troknite.troknite.domain.Product;
import com.example.troknite.troknite.domain.Users;
import com.example.troknite.troknite.model.ProductDTO;
import com.example.troknite.troknite.repos.ProductRepository;
import com.example.troknite.troknite.repos.UsersRepository;
import com.example.troknite.troknite.util.NotFoundException;

import jakarta.transaction.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UsersRepository usersRepository;

    public ProductService(final ProductRepository productRepository,
            final UsersRepository usersRepository) {
        this.productRepository = productRepository;
        this.usersRepository = usersRepository;
    }

    public List<ProductDTO> findAll() {
        final List<Product> products = productRepository.findAll(Sort.by("id"));
        return products.stream()
                .map((product) -> mapToDTO(product, new ProductDTO()))
                .toList();
    }

    public ProductDTO get(final Long id) {
        return productRepository.findById(id)
                .map(product -> mapToDTO(product, new ProductDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ProductDTO productDTO) throws IOException {
        final Product product = new Product();
        mapToEntity(productDTO, product);
        return productRepository.save(product).getId();
    }

    public void update(Long id, ProductDTO productDTO, List<byte[]> images) throws IOException {
        Product product = productRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        
        mapToEntity(productDTO, product);
        
        if (!CollectionUtils.isEmpty(images)) {
            product.getImages().addAll(images);
        }
        
        productRepository.save(product);
    }

    public void delete(final Long id) {
        productRepository.deleteById(id);
    }
    
    public List<ProductDTO> findAllByCategory(String category) {
        final List<Product> products = productRepository.findAllByCategory(category);
        return products.stream()
                .map((product) -> mapToDTO(product, new ProductDTO()))
                .toList();
    }

    public List<ProductDTO> findAllByNameContaining(String name) {
        final List<Product> products = productRepository.findAllByNameContaining(name);
        return products.stream()
                .map((product) -> mapToDTO(product, new ProductDTO()))
                .toList();
    }


    private ProductDTO mapToDTO(final Product product, final ProductDTO productDTO) {
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setCategory(product.getCategory());
        productDTO.setDescription(product.getDescription());
        productDTO.setValue(product.getValue());
        productDTO.setAvailable(product.getAvailable());
        if (product.getImages() != null) {
            List<MultipartFile> multipartFiles = product.getImages().stream()
                    .map(bytes -> new CustomMultipartFile(bytes, "file"))
                    .collect(Collectors.toList());

            productDTO.setImageFiles(multipartFiles);
        }
        productDTO.setUsers(product.getUsers() == null ? null : product.getUsers().getId());
        return productDTO;
    }

    private Product mapToEntity(final ProductDTO productDTO, final Product product) throws IOException {
        product.setName(productDTO.getName());
        product.setCategory(productDTO.getCategory());
        product.setDescription(productDTO.getDescription());
        product.setValue(productDTO.getValue());
        product.setAvailable(productDTO.getAvailable());
        if (!CollectionUtils.isEmpty(productDTO.getImageFiles())) {
            List<byte[]> images = productDTO.getImageFiles().stream()
                .map(file -> {
                    try {
                        return file.getBytes();
                    } catch (IOException e) {
                        // Handle or log the exception
                        return null;
                    }
                })
                .collect(Collectors.toList());
                
            product.setImages(images);
        }
        
        final Users users = productDTO.getUsers() == null ? null : usersRepository.findById(productDTO.getUsers())
                .orElseThrow(() -> new NotFoundException("users not found"));
        product.setUsers(users);
        return product;
    }
    
    public List<Product> findAllByUser(Users user) {
        return productRepository.findAllByUsers(user);
    }

    @Transactional
    public void addProductImages(Long productId, List<byte[]> images) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.getImages().addAll(images);
            productRepository.save(product);
        } else {
            throw new NotFoundException("Product not found");
        }
    }
}
