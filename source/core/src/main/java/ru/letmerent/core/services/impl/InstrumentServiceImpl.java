package ru.letmerent.core.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.letmerent.core.entity.Instrument;
import ru.letmerent.core.entity.User;
import ru.letmerent.core.repositories.InstrumentRepository;
import ru.letmerent.core.services.InstrumentService;

@Service
@RequiredArgsConstructor
public class InstrumentServiceImpl implements InstrumentService {

    final InstrumentRepository instrumentRepository;

    @Override
    public Page<Instrument> getAllInstruments(Pageable pageable) {
        return instrumentRepository.findAll(pageable);
    }

    @Override
    public Instrument getInstrumentById(Long id) {
        return instrumentRepository.getById(id);
    }

    @Override
    public Instrument createInstrument(Instrument instrument) {
        return instrumentRepository.save(instrument);
    }
}
