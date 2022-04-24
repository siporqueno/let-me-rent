package ru.letmerent.core.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.letmerent.core.dto.InstrumentForListDto;
import ru.letmerent.core.dto.InstrumentInfoDto;
import ru.letmerent.core.dto.IntervalDto;
import ru.letmerent.core.entity.Category;
import ru.letmerent.core.entity.Instrument;
import ru.letmerent.core.entity.Picture;
import ru.letmerent.core.entity.User;
import ru.letmerent.core.services.CategoryService;
import ru.letmerent.core.services.OrderItemService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class InstrumentConverter {

    private final CategoryService categoryService;
    private final OrderItemService orderItemService;

    public InstrumentForListDto toListDto(Instrument instrument) {
        InstrumentForListDto dto = new InstrumentForListDto();

        dto.setId(instrument.getId());
        dto.setTitle(instrument.getTitle());
        dto.setBrandName(instrument.getBrand().getBrandName());
        dto.setPrice(instrument.getPrice());
        dto.setFee(instrument.getFee());
        dto.setOwnerUsername(instrument.getUser().getUserName());

        Category category = categoryService.getCategoryById(instrument.getCategoryId());
        dto.setCategoryName(category.getName());

        dto.setAvatarPictureUrl(instrument.getPictures().stream().findFirst().map(Picture::getName).orElse(null));

        return dto;
    }

    public InstrumentInfoDto toInstrumentInfoDto(Instrument instrument) {
        InstrumentInfoDto dto = new InstrumentInfoDto();

        dto.setId(instrument.getId());
        dto.setTitle(instrument.getTitle());
        dto.setPicturesNames(instrument.getPictures().stream().map(Picture::getName).collect(toList()));
        dto.setBrandName(instrument.getBrand().getBrandName());
        dto.setPrice(instrument.getPrice());
        dto.setFee(instrument.getFee());
        dto.setDescription(instrument.getDescription());
        dto.setIntervals(initNoRentIntervals(instrument));
        
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
    
    private List<IntervalDto> initNoRentIntervals(Instrument instrument) {
        List<IntervalDto> noRentIntervals = new ArrayList<>();
        List<IntervalDto> rentIntervals = orderItemService.findAllByInstrumentId(instrument.getId())
            .stream()
            .map(orderItem -> new IntervalDto(orderItem.getStartDate(), orderItem.getEndDate()))
            .sorted(Comparator.comparing(IntervalDto::getDateStart))
            .collect(toList());
        
        LocalDateTime startFreeInterval = instrument.getStartDate();
        LocalDateTime endFreeInterval;
        for (int i = 0; i < rentIntervals.size(); i++) {
            if (startFreeInterval != rentIntervals.get(i).getDateStart()) {
                endFreeInterval = rentIntervals.get(i).getDateStart().minusSeconds(1);
            } else {
                startFreeInterval = rentIntervals.get(i).getDateFinish().plusSeconds(1);
                continue;
            }
            
            addIfValid(noRentIntervals, new IntervalDto(startFreeInterval, endFreeInterval));
            
            startFreeInterval = rentIntervals.get(i).getDateFinish().plusSeconds(1);
            if (i == rentIntervals.size() - 1 && rentIntervals.get(i).getDateFinish() != instrument.getEndDate()) {
                addIfValid(noRentIntervals, new IntervalDto(startFreeInterval, instrument.getEndDate()));
            }
        }
        
        return noRentIntervals.stream().sorted(Comparator.comparing(IntervalDto::getDateStart).reversed()).collect(toList());
    }
    
    private void addIfValid(List<IntervalDto> noRentIntervals, IntervalDto intervalDto) {
        if (intervalDto.getDateStart().isBefore(intervalDto.getDateFinish())) {
            noRentIntervals.add(intervalDto);
        }
    }
}
