package by.ledza.orderlab.exceptions;


public class UserNotFoundedException extends RuntimeException {

    public UserNotFoundedException() {
        super("There is no such user!");
    }
}
