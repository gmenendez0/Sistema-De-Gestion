package com.example.soporte.services.service;

import com.example.soporte.exceptions.RepositoryException;

import java.util.function.Supplier;

public abstract class Service{
    public Service() {}

    /**
     * Executes a repository operation safely using a {@link Supplier} callback.
     *
     * @param supplierCallback the callback function that supplies the repository operation
     * @param <J> the type of result returned by the repository operation
     * @return the result of the repository operation
     * @throws RepositoryException if an exception occurs during the repository operation
     */
    protected <J> J executeRepositorySupplierSafely(Supplier<J> supplierCallback) throws RepositoryException {
        try {
            return supplierCallback.get();
        } catch (Exception e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    /**
     * Executes a repository operation safely using a {@link Runnable} callback.
     *
     * @param runnableCallback the callback function that performs the repository operation
     * @throws RepositoryException if an exception occurs during the repository operation
     */
    protected void executeRepositoryRunnableSafely(Runnable runnableCallback) throws RepositoryException {
        try {
            runnableCallback.run();
        } catch (Exception e) {
            throw new RepositoryException(e.getMessage());
        }
    }
}
