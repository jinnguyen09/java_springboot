package webapp.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class User {

    // Setter va Getter
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String role;
    @Setter
    @Getter
    private String fullname;
    @Setter
    @Getter
    private int age;
    @Setter
    @Getter
    private String gender;
    @Setter
    @Getter
    private Number phone;
    @Setter
    @Getter
    private String address;
    @Setter
    @Getter
    private String avatar;
    @Setter
    @Getter
    private String status;
    @Setter
    @Getter
    private LocalDateTime created_at;
    @Setter
    @Getter
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
