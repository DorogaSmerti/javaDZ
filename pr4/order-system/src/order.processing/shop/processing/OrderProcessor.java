package shop.processing;

import shop.model.Order;
import shop.model.Product;
import shop.storage.OrderStore;

public final class OrderProcessor<T extends Product> {
    private final OrderStore store;

    public OrderProcessor(OrderStore store) {
        this.store = store;
    }

    public void processOrder(Order<T> order) throws InvalidOrderException {
        if (order == null || order.id() == null || order.product() == null) {
            throw new InvalidOrderException("Замовлення має містити ідентифікатор і товар.");
        }
        String name = order.product().name();
        if (name == null || name.isBlank()) {
            throw new InvalidOrderException("Вкажіть назву товару.");
        }
        if (!store.save(order)) {
            throw new InvalidOrderException("Замовлення з таким ідентифікатором уже оброблено.");
        }
        System.out.printf("[%s] Оброблено: %s%n", Thread.currentThread().getName(), name);
    }
}
