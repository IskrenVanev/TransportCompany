package org.example.CustomExceptions;

public class NoTransportVehicleFoundException extends RuntimeException {

    public NoTransportVehicleFoundException(long transportVehicleId) {
        super("TransportVehicle with ID " + transportVehicleId + " not found.");
    }
}
