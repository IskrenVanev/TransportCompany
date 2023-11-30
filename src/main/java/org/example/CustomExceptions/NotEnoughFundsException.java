package org.example.CustomExceptions;

public class NotEnoughFundsException extends RuntimeException{
    public NotEnoughFundsException(long clientId) {
        super("client with ID " + clientId + " does not have enough money.");
    }
}
