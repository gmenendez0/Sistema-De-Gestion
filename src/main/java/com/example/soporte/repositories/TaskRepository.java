package com.example.soporte.repositories;

import com.example.soporte.models.ExternalEntities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Long> {

}
