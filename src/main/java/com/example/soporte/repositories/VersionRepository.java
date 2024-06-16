package com.example.soporte.repositories;

import com.example.soporte.models.Product.Version;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VersionRepository extends JpaRepository<Version, Long> {

}
