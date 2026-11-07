module order.workers {
    requires order.processing;
    requires javafaker;
    requires java.sql; // Needed by JavaFaker's YAML parser at runtime.
}
