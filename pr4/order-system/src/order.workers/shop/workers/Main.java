package shop.workers;

import com.github.javafaker.Faker;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import shop.model.Clothing;
import shop.model.Electronics;
import shop.model.Order;
import shop.processing.InvalidOrderException;
import shop.processing.OrderProcessor;
import shop.storage.OrderStore;

public final class Main {
    public static void main(String[] args) throws InterruptedException {
        Faker faker = new Faker();
        Clothing clothing = Clothing.builder()
                .name(faker.commerce().productName())
                .size(faker.options().option("S", "M", "L"))
                .build();
        Electronics electronics = Electronics.builder()
                .name(faker.commerce().productName())
                .brand(faker.company().name())
                .build();

        OrderStore store = new OrderStore();
        OrderProcessor<Clothing> clothingProcessor = new OrderProcessor<>(store);
        OrderProcessor<Electronics> electronicsProcessor = new OrderProcessor<>(store);
        Order<Clothing> clothingOrder = new Order<>(UUID.randomUUID(), clothing);
        Order<Electronics> electronicsOrder = new Order<>(UUID.randomUUID(), electronics);
        Order<Clothing> invalidOrder = new Order<>(UUID.randomUUID(), null);

        try (OrderWorkers workers = new OrderWorkers()) {
            List<Future<Void>> tasks = List.of(
                    workers.submit(clothingOrder, clothingProcessor::processOrder),
                    workers.submit(electronicsOrder, electronicsProcessor::processOrder),
                    workers.submit(invalidOrder, clothingProcessor::processOrder));

            for (Future<Void> task : tasks) {
                try {
                    task.get();
                } catch (ExecutionException e) {
                    if (e.getCause() instanceof InvalidOrderException invalid) {
                        System.out.println("Помилка: " + invalid.getMessage());
                    } else {
                        throw new IllegalStateException("Не вдалося обробити замовлення.", e.getCause());
                    }
                }
            }
        }

        System.out.println("Збережено замовлень: " + store.all().size());
    }
}
