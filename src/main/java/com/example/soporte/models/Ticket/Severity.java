package com.example.soporte.models.Ticket;

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

    public abstract Duration getMaxResolutionTime();
}
