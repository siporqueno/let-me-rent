package ru.letmerent.core.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.letmerent.core.entity.Instrument;
import ru.letmerent.core.entity.User;

import java.math.BigDecimal;
import java.util.Collection;

@Controller
@RequestMapping("/api/v1/instruments")
public interface IInstrumentController {

    Collection<Instrument> getAllInstrument();

    Collection<Instrument> getAllInstrumentByOwner(User user);

    Instrument getInstrumentById(Long id);

    void addNewInstrument(Instrument instrument);

    boolean deleteInstrument(Long id);

    boolean deleteInstrument(Instrument instrument);

    Instrument modifyInstrument(Instrument instrument);

    Instrument changeInstrumentPrice(Long id, BigDecimal price);

}
