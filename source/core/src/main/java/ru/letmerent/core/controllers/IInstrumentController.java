package ru.letmerent.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.letmerent.core.entity.Instrument;
import ru.letmerent.core.entity.User;
import ru.letmerent.core.services.InstrumentService;

import java.math.BigDecimal;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/instruments")
public class IInstrumentController {

    private final InstrumentService instrumentService;

    @GetMapping
    Collection<Instrument> getAllInstrument() {
        return instrumentService.getAllInstruments();
    }

    Collection<Instrument> getAllInstrumentByOwner(User user) {
        throw new UnsupportedOperationException();
    }

    Instrument getInstrumentById(Long id) {
        return null;
    }

    void addNewInstrument(Instrument instrument) {
        throw new UnsupportedOperationException();
    }

    boolean deleteInstrument(Long id) {
        throw new UnsupportedOperationException();
    }

    boolean deleteInstrument(Instrument instrument) {
        throw new UnsupportedOperationException();
    }

    Instrument modifyInstrument(Instrument instrument) {
        throw new UnsupportedOperationException();
    }

    Instrument changeInstrumentPrice(Long id, BigDecimal price) {
        throw new UnsupportedOperationException();
    }
    
    Instrument changeInstrumentFee(Long id, BigDecimal price) {
        throw new UnsupportedOperationException();
    }

}
