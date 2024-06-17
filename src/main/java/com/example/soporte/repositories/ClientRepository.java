package com.example.soporte.repositories;

import com.example.soporte.models.ExternalEntities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {

}
