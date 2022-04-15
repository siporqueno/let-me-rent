package ru.letmerent.core.converters;

import org.springframework.stereotype.Component;
import ru.letmerent.core.dto.InstrumentDto;
import ru.letmerent.core.entity.Instrument;

@Component
public class InstrumentConverter {

    public InstrumentDto toListDto(Instrument instrument) {
        InstrumentDto dto = new InstrumentDto();

        dto.setId(instrument.getId());
        dto.setTitle(instrument.getTitle());
        dto.setBrandName(instrument.getBrand().getBrandName());
        dto.setPrice(instrument.getPrice());
        dto.setFee(instrument.getFee());

        return dto;
    }
}
