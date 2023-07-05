package com.example.troknite.troknite.repos;

import com.example.troknite.troknite.domain.Hash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashRepository extends JpaRepository<Hash, Long> {
}
