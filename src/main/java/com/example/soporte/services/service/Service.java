package com.example.soporte.services.service;

import com.example.soporte.exceptions.RepositoryException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.function.Supplier;

public abstract class Service <T, ID> {
    protected JpaRepository<T, ID> repository;

    public Service(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    protected <J> J executeRepositorySupplierSafely(Supplier<J> supplierCallback) throws RepositoryException {
        try {
            return supplierCallback.get();
        } catch (Exception e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    protected void executeRepositoryRunnableSafely(Runnable runnableCallback) throws RepositoryException {
        try {
            runnableCallback.run();
        } catch (Exception e) {
            throw new RepositoryException(e.getMessage());
        }
    }
}
