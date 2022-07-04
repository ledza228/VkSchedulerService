package by.ledza.orderlab.repository;

import by.ledza.orderlab.model.UserCreds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredsRepository extends JpaRepository<UserCreds, String> {
}
