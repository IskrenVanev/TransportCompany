package org.example.CustomExceptions;

public class NoCompanyException extends RuntimeException{
    public NoCompanyException(long companyExceptionId) {
        super("Company with ID " + companyExceptionId + " not found.");
    }
}
