package by.ledza.orderlab.exceptions;

public class OrderNotFoundedException extends RuntimeException {
    public OrderNotFoundedException(Integer id) {
        super("There is no " + id + " order ");
    }
}
