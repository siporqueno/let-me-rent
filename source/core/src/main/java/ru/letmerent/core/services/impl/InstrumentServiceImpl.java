package ru.letmerent.core.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.letmerent.core.dto.CriteriaSearch;
import ru.letmerent.core.dto.InstrumentRentDto;
import ru.letmerent.core.entity.Instrument;
import ru.letmerent.core.repositories.InstrumentRepository;
import ru.letmerent.core.repositories.specifications.InstrumentSpecification;
import ru.letmerent.core.services.InstrumentService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InstrumentServiceImpl implements InstrumentService {

    final InstrumentRepository instrumentRepository;

    final InstrumentSpecification instrumentSpecification;

    @Override
    @Transactional
    public Page<Instrument> getAllInstruments(Pageable pageable, CriteriaSearch criteriaSearch) {
        return instrumentRepository.findAll(instrumentSpecification.spec(criteriaSearch), pageable);
    }

    @Override
    public Optional<Instrument> getInstrumentById(Long id) {
        return Optional.of(instrumentRepository.getById(id));
    }

    @Override
    public Instrument createInstrument(Instrument instrument) {
        return instrumentRepository.save(instrument);
    }
    
    @Override
    public Collection<InstrumentRentDto> getInstrumentRents(Long instrumentId, Long userId) {
        LocalDateTime from = LocalDate.now().minusMonths(6).atStartOfDay();
        LocalDateTime to = LocalDate.now().plusMonths(6).atStartOfDay();
        
        return instrumentRepository.getInstrumentRents(instrumentId, userId, from, to);
    }
}
