package com.example.troknite.troknite.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("com.example.troknite.troknite.domain")
@EnableJpaRepositories("com.example.troknite.troknite.repos")
@EnableTransactionManagement
public class DomainConfig {
}
