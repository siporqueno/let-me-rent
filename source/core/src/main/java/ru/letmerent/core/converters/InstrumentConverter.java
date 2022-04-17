package ru.letmerent.core.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.letmerent.core.dto.InstrumentDto;
import ru.letmerent.core.dto.InstrumentForListDto;
import ru.letmerent.core.dto.InstrumentInfoDto;
import ru.letmerent.core.entity.Category;
import ru.letmerent.core.entity.Instrument;
import ru.letmerent.core.entity.Picture;
import ru.letmerent.core.entity.User;
import ru.letmerent.core.services.CategoryService;

import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class InstrumentConverter {

    private final CategoryService categoryService;

    public InstrumentForListDto toListDto(Instrument instrument) {
        InstrumentForListDto dto = new InstrumentForListDto();

        dto.setId(instrument.getId());
        dto.setTitle(instrument.getTitle());
        dto.setBrandName(instrument.getBrand().getBrandName());
        dto.setPrice(instrument.getPrice());
        dto.setFee(instrument.getFee());
        dto.setOwnerUsername(instrument.getUser().getUserName());

        Category category = categoryService.getCategoryById(instrument.getId());
        dto.setCategoryName(category.getName());

        dto.setAvatarPictureUrl(instrument.getPictures().stream().findFirst().map(Picture::getUrl).orElse(null));

        return dto;
    }

    public InstrumentInfoDto toInstrumentInfoDto(Instrument instrument) {
        InstrumentInfoDto dto = new InstrumentInfoDto();

        dto.setId(instrument.getId());
        dto.setTitle(instrument.getTitle());
        dto.setPicturesUrls(instrument.getPictures().stream().map(Picture::getUrl).collect(toList()));
        dto.setBrandName(instrument.getBrand().getBrandName());
        dto.setPrice(instrument.getPrice());
        dto.setFee(instrument.getFee());
        dto.setDescription(instrument.getDescription());
        dto.setIntervals(null); //todo описать логику получения интервалов доступности

        User owner = instrument.getUser();
        dto.setOwnerUsername(owner.getUserName());
        dto.setOwnerEmail(owner.getEmail());
        dto.setOwnerFirstName(owner.getFirstName());
        dto.setOwnerSecondName(owner.getSecondName());
        dto.setOwnerLastName(owner.getLastName());

        Category category = categoryService.getCategoryById(instrument.getId());
        dto.setCategoryName(category.getName());

        return dto;
    }
}
