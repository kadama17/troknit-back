package com.example.troknite.troknite.repos;

import com.example.troknite.troknite.domain.Users;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UsersRepository extends JpaRepository<Users, Long> {

	Optional<Users> findByEmail(String email);
  
    Boolean existsByEmail(String email);
	
}
