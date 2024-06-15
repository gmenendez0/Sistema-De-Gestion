package com.example.soporte.models.Ticket;

import java.time.Duration;

//TODO Corregir las horas de resolución de los niveles de severidad
public enum Severity {
    S1{
        @Override
        public Duration getMaxResolutionTime() {
            return Duration.ofHours(1);
        }
    },
    S2{
        @Override
        public Duration getMaxResolutionTime() {
            return Duration.ofHours(4);
        }
    },
    S3{
        @Override
        public Duration getMaxResolutionTime() {
            return Duration.ofHours(8);
        }
    },
    S4{
        @Override
        public Duration getMaxResolutionTime() {
            return Duration.ofHours(24);
        }
    };

    public abstract Duration getMaxResolutionTime();
}
