package se.threegorillas.exception;

/**
 * Created by joanne on 05/04/16.
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }

}
