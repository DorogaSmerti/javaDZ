import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private int id;
    private String name;
    private double price;
    private String description;
    private Category category;

    public Product(int id, String name, double price, String description) {
        this(id, name, price, description, null);
    }
}
