package com.example.soporte.services;

import com.example.soporte.models.Product.Product;
import com.example.soporte.models.Product.Version;
import com.example.soporte.models.Ticket.Ticket;
import com.example.soporte.repositories.VersionRepository;

import java.util.Collection;
import java.util.Optional;

@org.springframework.stereotype.Service
public class VersionService extends Service<Version, Long> {
    public VersionService(VersionRepository repository) {
        super(repository);
    }

    public Collection<Version> getVersions() {
        return executeRepositorySupplierSafely(() -> repository.findAll());
    }

    public Collection<Ticket> getTicketsByVersion(Long versionId) {
        Optional<Version> version = executeRepositorySupplierSafely(() -> repository.findById(versionId));

        return version.<Collection<Ticket>>map(Version::getTickets).orElse(null);
    }
    public Version getVersionById(Long versionId) {
        return repository.findById(versionId)
                .orElseThrow(() -> new IllegalArgumentException("Version not found"));
    }
    public Product getProductByVersionId(Long versionId) {
        Version version = getVersionById(versionId);
        return version.getProduct();
    }
}
