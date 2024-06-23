package com.example.soporte.models.Ticket;

import com.example.soporte.exceptions.InvalidArgumentsException;

public enum Status{
    NUEVO,
    EN_PROGRESO,
    ESPERANDO_CLIENTE,
    ESPERANDO_DESARROLLO,
    RESUELTO_ESPERANDO_CONFIRMACION,
    CERRADO,
    BLOQUEADO;

    public static Status fromString(String status){
        try {
            return Status.valueOf(status.toUpperCase());
        } catch(IllegalArgumentException exception){
            throw new InvalidArgumentsException("Invalid status: " + status);
        }
    }
}
