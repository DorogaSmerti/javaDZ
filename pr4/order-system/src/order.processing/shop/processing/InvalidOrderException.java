package shop.processing;

public final class InvalidOrderException extends Exception {
    public InvalidOrderException(String message) {
        super(message);
    }
}
