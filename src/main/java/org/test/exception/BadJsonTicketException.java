package org.test.exception;

public class BadJsonTicketException extends RuntimeException{

    public BadJsonTicketException(String message) {
        super(message);
    }

    public BadJsonTicketException(String message, Throwable cause) {
        super(message, cause);
    }
}
