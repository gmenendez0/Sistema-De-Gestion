package com.example.soporte.services;

import org.springframework.data.jpa.repository.JpaRepository;

public abstract class Service <T, ID> {

    protected JpaRepository<T, ID> repository;

    public Service(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }
}
