package cz.edhouse.javaee.jpa;

/**
 *
 * @author Frantisek Spacek
 */
public class DataStorageException extends RuntimeException {

    public DataStorageException(String message) {
        super(message);
    }

    public DataStorageException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
