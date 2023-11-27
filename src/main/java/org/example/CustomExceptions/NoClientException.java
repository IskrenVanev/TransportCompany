package org.example.CustomExceptions;

public class NoClientException extends RuntimeException{
    public NoClientException(long clientExceptionId) {
        super("Client with ID " + clientExceptionId + " not found.");
    }
}
