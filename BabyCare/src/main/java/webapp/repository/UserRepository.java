package webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webapp.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    // login by username or email
    Optional<User> findByUsernameOrEmail(String username, String email);
}
