package ru.letmerent.core.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.letmerent.core.entity.Instrument;
import ru.letmerent.core.repositories.InstrumentRepository;
import ru.letmerent.core.services.InstrumentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstrumentServiceImpl implements InstrumentService {

    final InstrumentRepository instrumentRepository;

    @Override
    public List<Instrument> getAllInstruments() {
        return instrumentRepository.findAll();
    }

    @Override
    public Instrument getInstrumentById(Long id) {
        return instrumentRepository.getById(id);
    }
}
