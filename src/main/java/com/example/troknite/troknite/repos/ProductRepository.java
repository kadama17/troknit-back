package com.example.troknite.troknite.repos;

import com.example.troknite.troknite.domain.Product;
import com.example.troknite.troknite.domain.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByUsers(Users user);
    List<Product> findAllByCategory(String category);
    List<Product> findAllByNameContaining(String name);


}
