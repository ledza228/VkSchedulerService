package by.ledza.orderlab.exceptions;

public class VkTokenException extends RuntimeException{
    public VkTokenException() {
        super("User hasn't vk token");
    }

    public VkTokenException(String message) {
        super(message);
    }
}
