package com.batanasov.javaTransportCompanyProjectSPRING.exceptions;
/**
 * Custom exception class representing a scenario where an employee is not qualified to operate a specific vehicle.
 * This exception is thrown when an employee lacks the necessary qualification for a vehicle assigned in a transport contract.
 */
public class EmployeeNotQualifiedForVehicleException extends Exception {
    public EmployeeNotQualifiedForVehicleException(String message) {
    }
}
