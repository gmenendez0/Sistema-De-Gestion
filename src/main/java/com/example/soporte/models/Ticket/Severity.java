package com.example.soporte.models.Ticket;

import com.example.soporte.exceptions.InvalidArgumentsException;

import java.time.Duration;

public enum Severity {
    S1{
        @Override
        public Duration getMaxResolutionTime() {
            return Duration.ofDays(7);
        }
    },
    S2{
        @Override
        public Duration getMaxResolutionTime() {
            return Duration.ofDays(31);
        }
    },
    S3{
        @Override
        public Duration getMaxResolutionTime() {
            return Duration.ofDays(90);
        }
    },
    S4{
        @Override
        public Duration getMaxResolutionTime() {
            return Duration.ofDays(365);
        }
    };

    public static Severity fromString(String severity){
        try {
            return Severity.valueOf(severity.toUpperCase());
        } catch(IllegalArgumentException exception){
            throw new InvalidArgumentsException("Invalid severity: " + severity);
        }
    }

    public abstract Duration getMaxResolutionTime();
}
