package ru.letmerent.core.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.letmerent.core.dto.CriteriaSearch;
import ru.letmerent.core.dto.InstrumentRentDto;
import ru.letmerent.core.entity.Instrument;

import java.util.Collection;
import java.util.Optional;

public interface InstrumentService {

    Page<Instrument> getAllInstruments(Pageable pageable, CriteriaSearch criteriaSearch);
    
    Page<Instrument> getAllUserInstruments(Pageable pageable, String username);
    
    Instrument createInstrument(Instrument instrument);
    
    Optional<Instrument> getInstrumentById(Long id);
    
    Instrument updateInstrument(Instrument instrument);
    
    Collection<InstrumentRentDto> getInstrumentRents(Long userId, Long instrumentId);
    
    void remove(Long instrumentId);
}
