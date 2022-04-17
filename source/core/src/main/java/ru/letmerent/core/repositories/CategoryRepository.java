package ru.letmerent.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.letmerent.core.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
