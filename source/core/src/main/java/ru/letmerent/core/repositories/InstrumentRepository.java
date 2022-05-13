package ru.letmerent.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.letmerent.core.dto.InstrumentRentDto;
import ru.letmerent.core.entity.Instrument;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface InstrumentRepository extends JpaRepository<Instrument, Long>, JpaSpecificationExecutor<Instrument> {
@Query(value = "select new ru.letmerent.core.dto.InstrumentRentDto(oi.startDate, oi.endDate, u.userName) from OrderItem oi " +
    "join Instrument i on i.id = oi.instrument.id " +
    "join Order o on o.id = oi.order.id " +
    "join User u on u.id = o.user.id " +
    "where i.id =?1 and i.user.id=?2 " +
    "and oi.startDate >= ?3 and oi.endDate <= ?4")
    Collection<InstrumentRentDto> getInstrumentRents(Long instrumentId, Long userId, LocalDateTime from, LocalDateTime to );
}
