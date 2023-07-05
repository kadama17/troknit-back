package com.example.troknite.troknite.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;


@Entity
public class Hash {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 555)
    private String hash;

    @OneToOne(mappedBy = "hash", fetch = FetchType.LAZY)
    private Transfer transfer;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(final String hash) {
        this.hash = hash;
    }

    public Transfer getTransfer() {
        return transfer;
    }

    public void setTransfer(final Transfer transfer) {
        this.transfer = transfer;
    }

}
