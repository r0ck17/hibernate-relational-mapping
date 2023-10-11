package by.javaguru.exception;

public class DaoException extends RuntimeException {
    public DaoException(String message, Throwable cause) {
        super(cause);
    }
    public DaoException(Throwable cause) {
        super(cause);
    }
}
