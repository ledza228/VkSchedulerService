package by.ledza.orderlab.repository;

import by.ledza.orderlab.model.Order;
import by.ledza.orderlab.model.UserCreds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByOwner(UserCreds userCreds);

    List<Order> findAllByIsActiveAndDateTimeBefore(Boolean isActive, OffsetDateTime dateTime);
}
