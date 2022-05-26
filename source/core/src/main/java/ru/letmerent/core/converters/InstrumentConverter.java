package ru.letmerent.core.converters;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.letmerent.core.dto.InstrumentForListDto;
import ru.letmerent.core.dto.InstrumentInfoDto;
import ru.letmerent.core.dto.IntervalDto;
import ru.letmerent.core.entity.Brand;
import ru.letmerent.core.entity.Category;
import ru.letmerent.core.entity.Instrument;
import ru.letmerent.core.entity.OrderItem;
import ru.letmerent.core.entity.Picture;
import ru.letmerent.core.entity.User;
import ru.letmerent.core.repositories.PictureRepository;
import ru.letmerent.core.services.BrandService;
import ru.letmerent.core.services.CategoryService;
import ru.letmerent.core.services.OrderItemService;
import ru.letmerent.core.services.PictureStorageService;

import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
@Slf4j
public class InstrumentConverter {
    @Value("${server.port}")
    private int port;
    @Value("${server.servlet.context-path}")
    private String contextPath;
    
    private final CategoryService categoryService;
    private final PictureStorageService pictureStorageService;
    private final OrderItemService orderItemService;
    private final BrandService brandService;
    private final PictureRepository pictureRepository;
    
    public InstrumentForListDto toListDto(Instrument instrument) {
        InstrumentForListDto dto = new InstrumentForListDto();

        dto.setId(instrument.getId());
        dto.setTitle(instrument.getTitle());
        dto.setBrandName(instrument.getBrand().getBrandName());
        dto.setPrice(instrument.getPrice());
        dto.setFee(instrument.getFee());
        dto.setOwnerUsername(instrument.getUser().getUserName());

        Category category = categoryService.findCategoryById(instrument.getCategoryId());
        dto.setCategoryName(category.getName());

        dto.setAvatarPictureUrl(instrument.getPictures().stream()
            .findFirst()
            .map(p->getUrl(p.getName()))
            .orElse(null));

        return dto;
    }

    public InstrumentInfoDto toInstrumentInfoDto(Instrument instrument) {
        InstrumentInfoDto dto = new InstrumentInfoDto();

        dto.setId(instrument.getId());
        dto.setTitle(instrument.getTitle());
    
        List<Picture> instrumentPictures = pictureStorageService.findAllPictureByInstrumentId(instrument.getId());
        if (!instrumentPictures.isEmpty()) {
            dto.setPictureUrls(instrumentPictures.stream()
                .map(p->getUrl(p.getName()))
                .collect(Collectors.toList()));
        }

        dto.setBrandName(instrument.getBrand().getBrandName());
        dto.setPrice(instrument.getPrice());
        dto.setFee(instrument.getFee());
        dto.setDescription(instrument.getDescription());
        dto.setIntervals(initNoRentIntervals(instrument));

        User owner = instrument.getUser();
        dto.setOwnerId(owner.getId());
        dto.setOwnerUsername(owner.getUserName());
        dto.setOwnerEmail(owner.getEmail());
        dto.setOwnerFirstName(owner.getFirstName());
        dto.setOwnerSecondName(owner.getSecondName());
        dto.setOwnerLastName(owner.getLastName());

        Category category = categoryService.findCategoryById(instrument.getCategoryId());
        dto.setCategoryName(category.getName());

        dto.setStartDate(instrument.getStartDate());
        dto.setEndDate(instrument.getEndDate());

        orderItemService.findAllByInstrumentId(instrument.getId())
                .stream()
                .map(OrderItem::getEndDate)
                .max(LocalDateTime::compareTo)
                .ifPresent(dto::setLastRentDate);

        return dto;
    }

    public Instrument toInstrument(InstrumentInfoDto instrumentDto, User user) {
        Optional<Brand> brand = brandService.findByBrandName(instrumentDto.getBrandName());
        if (brand.isEmpty()) {
            brand = Optional.of(brandService.createBrand(Brand.builder().brandName(instrumentDto.getBrandName()).startDate(LocalDateTime.now()).build()));
        }

        Optional<Category> category = categoryService.findCategoryByName(instrumentDto.getCategoryName());
        if (category.isEmpty()) {
            category = Optional.of(categoryService.createCategory(new Category(instrumentDto.getCategoryName(), null, LocalDateTime.now(), null)));
        }
        if (!instrumentDto.getPictureUrls().isEmpty()) {
            removePictures(instrumentDto.getId(), new ArrayList<>(instrumentDto.getPictureUrls()));
        }

        Instrument instrument = new Instrument();
        instrument.setId(instrumentDto.getId());
        instrument.setTitle(instrumentDto.getTitle());
        instrument.setDescription(instrumentDto.getDescription());
        instrument.setPrice(instrumentDto.getPrice());
        instrument.setFee(instrumentDto.getFee());
        instrument.setUser(user);
        instrument.setBrand(brand.get());
        instrument.setCategoryId(category.get().getId());
        instrument.setStartDate(instrumentDto.getStartDate());

        LocalDateTime endDate = isNull(instrumentDto.getEndDate()) ? LocalDateTime.of(2099, 12, 31, 0, 0) : instrumentDto.getEndDate();
        instrument.setEndDate(endDate);

        return instrument;
    }
    
    private void removePictures(Long instrumentId, List<String> pictureUrl) {
        pictureUrl.forEach(pi -> {
            try {
                String name = new URI(pi).getPath();
                int index = name.lastIndexOf("/");
                if (index > -1) {
                    name = name.substring(index+1);
                    pictureStorageService.deletePicture(instrumentId, pictureRepository.findByName(name).getId());
                }
            } catch (URISyntaxException e) {
                log.error("Can't init name: {}", e.getMessage());
            }
        });
    }
    
    @SneakyThrows
    private String getUrl(String pictureName) {
        String localHost = InetAddress.getLocalHost().getHostAddress();
        String pictureRequestMapping = "api/v1/pictures";
        
        return String.format("http://%s:%s%s/%s/%s", localHost, port, contextPath, pictureRequestMapping, pictureName);
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
