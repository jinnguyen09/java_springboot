package Exercise.demo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;
    private String description;
    private String manufacturer;

    public Product() {}

    public Product(String name, double price, String description, String manufacturer) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.manufacturer = manufacturer;
    }

}