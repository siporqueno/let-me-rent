package ru.letmerent.core.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.letmerent.core.entity.Instrument;
import ru.letmerent.core.entity.User;

public interface InstrumentService {

    Page<Instrument> getAllInstruments(Pageable pageable);
    Instrument getInstrumentById(Long id);
    Instrument createInstrument(Instrument instrument);
}
