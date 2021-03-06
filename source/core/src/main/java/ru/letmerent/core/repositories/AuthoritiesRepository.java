package ru.letmerent.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.letmerent.core.entity.GrantedAuthority;

@Repository
public interface AuthoritiesRepository extends JpaRepository<GrantedAuthority, Long> {
}
