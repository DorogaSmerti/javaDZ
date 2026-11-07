package shop.storage;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import shop.model.Order;

public final class OrderStore {
    private final ConcurrentMap<UUID, Order<?>> orders = new ConcurrentHashMap<>();

    public boolean save(Order<?> order) {
        return orders.putIfAbsent(order.id(), order) == null;
    }

    public List<Order<?>> all() {
        return List.copyOf(orders.values());
    }
}
