package com.example.troknite.troknite.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;


@Entity
public class Transfer {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String idFrom;

    @Column(nullable = false)
    private String idTo;

    @Column(nullable = false)
    private String addressFrom;

    @Column(nullable = false)
    private String addressTo;

    @Column(nullable = false)
    private String date;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hash_id", nullable = false, unique = true)
    private Hash hash;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getIdFrom() {
        return idFrom;
    }

    public void setIdFrom(final String idFrom) {
        this.idFrom = idFrom;
    }

    public String getIdTo() {
        return idTo;
    }

    public void setIdTo(final String idTo) {
        this.idTo = idTo;
    }

    public String getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(final String addressFrom) {
        this.addressFrom = addressFrom;
    }

    public String getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(final String addressTo) {
        this.addressTo = addressTo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public Hash getHash() {
        return hash;
    }

    public void setHash(final Hash hash) {
        this.hash = hash;
    }

}
