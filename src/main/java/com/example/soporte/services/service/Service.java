package com.example.soporte.services.service;

import com.example.soporte.exceptions.RepositoryException;

import java.util.function.Supplier;

public abstract class Service{
    public Service() {}

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
