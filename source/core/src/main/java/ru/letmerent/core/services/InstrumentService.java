package ru.letmerent.core.services;

import ru.letmerent.core.entity.Instrument;

import java.util.List;

public interface InstrumentService {

    List<Instrument> getAllInstruments();
    Instrument getInstrumentById(Long id);
}
