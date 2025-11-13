package webapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    // Setter va Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String email;

    private String role;

    private String fullname;

    private int age;

    private String gender;

    private String phone;

    private String address;

    private String avatar;

    private String status;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    // Constructor
    public String user() {
        return "";
    }

    public String user(String username, String password, String role, String fullname, int age,
                       String gender, String email, Number phone, String address,
                       String avatar, String status, String created_at, String updated_at) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.fullname = fullname;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.avatar = avatar;
        this.status = status;
        this.created_at = LocalDateTime.parse(created_at);
        this.updated_at = LocalDateTime.parse(updated_at);
        return "";
    }
}

