package ru.letmerent.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.letmerent.core.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
