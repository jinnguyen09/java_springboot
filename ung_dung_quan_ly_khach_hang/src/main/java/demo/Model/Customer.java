package demo.Model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Customer {
    private Long id;
    private String name;
    private String email;
    private String address;

    public Customer() {
    }

    public Customer(Long id, String name, String email, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
