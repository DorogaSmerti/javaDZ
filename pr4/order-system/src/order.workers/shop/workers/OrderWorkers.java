package shop.workers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import shop.model.Order;
import shop.model.Product;
import shop.processing.InvalidOrderException;

final class OrderWorkers implements AutoCloseable {
    @FunctionalInterface
    interface Handler<T extends Product> {
        void process(Order<T> order) throws InvalidOrderException;
    }

    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    <T extends Product> Future<Void> submit(Order<T> order, Handler<T> handler) {
        return executor.submit(() -> {
            handler.process(order);
            return null;
        });
    }

    @Override
    public void close() {
        executor.close();
    }
}
