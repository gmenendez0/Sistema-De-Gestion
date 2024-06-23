package com.example.soporte.repositories;

import com.example.soporte.models.Ticket.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("SELECT t FROM Ticket t WHERE t.version.id = :versionId")
    List<Ticket> getTicketsByVersion(Long versionId);

    @Query("SELECT t FROM Ticket t WHERE t.version.id = :versionId")
    Page<Ticket> getTicketsPageByVersionId(Long versionId, Pageable pageable);
}