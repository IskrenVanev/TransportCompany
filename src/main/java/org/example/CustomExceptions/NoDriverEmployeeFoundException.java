package org.example.CustomExceptions;

public class NoDriverEmployeeFoundException extends RuntimeException{
    public NoDriverEmployeeFoundException(long driverEmployeeId) {
        super("driverEmployee with ID " + driverEmployeeId + " not found.");
    }
}
