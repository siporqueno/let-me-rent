package ru.letmerent.core.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import ru.letmerent.core.dto.InstrumentInfoDto;
import ru.letmerent.core.dto.IntervalDto;
import ru.letmerent.core.entity.Brand;
import ru.letmerent.core.entity.Category;
import ru.letmerent.core.entity.Instrument;
import ru.letmerent.core.entity.Order;
import ru.letmerent.core.entity.OrderItem;
import ru.letmerent.core.entity.Picture;
import ru.letmerent.core.entity.User;
import ru.letmerent.core.services.CategoryService;
import ru.letmerent.core.services.impl.OrderItemServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.openMocks;

class InstrumentConverterTest {
    @Mock
    private User user;
    @Mock
    private Order order;
    @Mock
    private Brand brand;
    @Mock
    private OrderItemServiceImpl orderItemService;
    @Mock
    private CategoryService categoryService;
    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private InstrumentConverter instrumentConverter;
    
    private final LocalDateTime startDate = LocalDateTime.of(2022, 2, 20, 20, 30);
    private final LocalDateTime endDate = LocalDateTime.of(2022, 9, 20, 20, 30);
    private Instrument instrument;
    
    @BeforeEach
    void init() {
        openMocks(this);
        
        ReflectionTestUtils.setField(instrumentConverter, "orderItemService", orderItemService);
        ReflectionTestUtils.setField(instrumentConverter, "categoryService", categoryService);
        
        List<Picture> pictures = Collections.emptyList();
        Category category = new Category("test_name", "test_description", startDate, endDate);
        instrument = new Instrument(
            "test_title",
            "test_description",
            new BigDecimal(1000),
            new BigDecimal(100),
            startDate,
            endDate,
            1L,
            user,
            pictures,
            brand
        );
        
        doReturn(category).when(categoryService).findCategoryById(instrument.getId());
    }
    
    /**
     * При преобразовании инструмента в InstrumentDto, intervals инициализируются валидными данными(даты аренды
     * внутри периода сдачи инструмента).
     */
    @Test
    void instrumentDtoFromInstrument_1() {
        LocalDateTime startDateRent1 = LocalDateTime.of(2022, 4, 20, 20, 30);
        LocalDateTime endDateRent1 = LocalDateTime.of(2022, 5, 20, 20, 30);
        LocalDateTime startDateRent2 = LocalDateTime.of(2022, 5, 25, 20, 30);
        LocalDateTime endDateRent2 = LocalDateTime.of(2022, 6, 25, 20, 30);
        IntervalDto freeInterval1 = new IntervalDto(endDateRent2.plusSeconds(1), endDate);
        IntervalDto freeInterval2 = new IntervalDto(endDateRent1.plusSeconds(1), startDateRent2.minusSeconds(1));
        IntervalDto freeInterval3 = new IntervalDto(startDate, startDateRent1.minusSeconds(1));
        
        List<OrderItem> orderItems = Arrays.asList(
            new OrderItem(order, instrument, startDateRent1, endDateRent1, new BigDecimal(1000)),
            new OrderItem(order, instrument, startDateRent2, endDateRent2, new BigDecimal(2000))
        );
        
        doReturn(orderItems).when(orderItemService).findAllByInstrumentId(instrument.getId());
        
        InstrumentInfoDto instrumentInfoDto = instrumentConverter.toInstrumentInfoDto(instrument);
        
        assertEquals(3, instrumentInfoDto.getIntervals().size());
        assertEquals(freeInterval1, instrumentInfoDto.getIntervals().get(0));
        assertEquals(freeInterval2, instrumentInfoDto.getIntervals().get(1));
        assertEquals(freeInterval3, instrumentInfoDto.getIntervals().get(2));
    }
    
    /**
     * При преобразовании инструмента в InstrumentDto, intervals инициализируются валидными данными(дата 1й аренды
     * совпадает с датой начала сдачи инструмента).
     */
    @Test
    void instrumentDtoFromInstrument_2() {
        LocalDateTime endDateRent1 = LocalDateTime.of(2022, 5, 20, 20, 30);
        LocalDateTime startDateRent2 = LocalDateTime.of(2022, 5, 25, 20, 30);
        LocalDateTime endDateRent2 = LocalDateTime.of(2022, 6, 25, 20, 30);
        IntervalDto freeInterval1 = new IntervalDto(endDateRent2.plusSeconds(1), endDate);
        IntervalDto freeInterval2 = new IntervalDto(endDateRent1.plusSeconds(1), startDateRent2.minusSeconds(1));
        
        List<OrderItem> orderItems = Arrays.asList(
            new OrderItem(order, instrument, startDate, endDateRent1, new BigDecimal(1000)),
            new OrderItem(order, instrument, startDateRent2, endDateRent2, new BigDecimal(2000))
        );
        
        doReturn(orderItems).when(orderItemService).findAllByInstrumentId(instrument.getId());
        
        InstrumentInfoDto instrumentInfoDto = instrumentConverter.toInstrumentInfoDto(instrument);
        
        assertEquals(2, instrumentInfoDto.getIntervals().size());
        assertEquals(freeInterval1, instrumentInfoDto.getIntervals().get(0));
        assertEquals(freeInterval2, instrumentInfoDto.getIntervals().get(1));
    }
    
    /**
     * При преобразовании инструмента в InstrumentDto, intervals инициализируются валидными данными(дата последней аренды
     * совпадает с датой конца сдачи инструмента).
     */
    @Test
    void instrumentDtoFromInstrument_3() {
        LocalDateTime startDateRent1 = LocalDateTime.of(2022, 4, 20, 20, 30);
        LocalDateTime endDateRent1 = LocalDateTime.of(2022, 5, 20, 20, 30);
        LocalDateTime startDateRent2 = LocalDateTime.of(2022, 5, 25, 20, 30);
        IntervalDto freeInterval1 = new IntervalDto(endDateRent1.plusSeconds(1), startDateRent2.minusSeconds(1));
        IntervalDto freeInterval2 = new IntervalDto(startDate, startDateRent1.minusSeconds(1));
        
        List<OrderItem> orderItems = Arrays.asList(
            new OrderItem(order, instrument, startDateRent1, endDateRent1, new BigDecimal(1000)),
            new OrderItem(order, instrument, startDateRent2, endDate, new BigDecimal(2000))
        );
        
        doReturn(orderItems).when(orderItemService).findAllByInstrumentId(instrument.getId());
        
        InstrumentInfoDto instrumentInfoDto = instrumentConverter.toInstrumentInfoDto(instrument);
        
        assertEquals(2, instrumentInfoDto.getIntervals().size());
        assertEquals(freeInterval1, instrumentInfoDto.getIntervals().get(0));
        assertEquals(freeInterval2, instrumentInfoDto.getIntervals().get(1));
    }
    
    /**
     * При преобразовании инструмента в InstrumentDto, intervals инициализируются валидными данными(дата последней аренды
     * совпадает с датой конца сдачи инструмента,дата 1й аренды совпадает с датой начала сдачи инструмента).
     */
    @Test
    void instrumentDtoFromInstrument_4() {
        LocalDateTime endDateRent1 = LocalDateTime.of(2022, 5, 20, 20, 30);
        LocalDateTime startDateRent2 = LocalDateTime.of(2022, 5, 25, 20, 30);
        IntervalDto freeInterval1 = new IntervalDto(endDateRent1.plusSeconds(1), startDateRent2.minusSeconds(1));
        
        List<OrderItem> orderItems = Arrays.asList(
            new OrderItem(order, instrument, startDate, endDateRent1, new BigDecimal(1000)),
            new OrderItem(order, instrument, startDateRent2, endDate, new BigDecimal(2000))
        );
        
        doReturn(orderItems).when(orderItemService).findAllByInstrumentId(instrument.getId());
        
        InstrumentInfoDto instrumentInfoDto = instrumentConverter.toInstrumentInfoDto(instrument);
        
        assertEquals(1, instrumentInfoDto.getIntervals().size());
        assertEquals(freeInterval1, instrumentInfoDto.getIntervals().get(0));
    }
    
    /**
     * При преобразовании инструмента в InstrumentDto, intervals инициализируются валидными данными(дата аренд
     * идут друг за другом, без перерыва).
     */
    @Test
    void instrumentDtoFromInstrument_5() {
        LocalDateTime startDateRent1 = LocalDateTime.of(2022, 4, 20, 20, 30);
        LocalDateTime endDateRent1 = LocalDateTime.of(2022, 5, 20, 20, 30);
        LocalDateTime startDateRent2 = LocalDateTime.of(2022, 5, 20, 20, 30,1);
        LocalDateTime endDateRent2 = LocalDateTime.of(2022, 6, 25, 20, 30);
        IntervalDto freeInterval1 = new IntervalDto(endDateRent2.plusSeconds(1), endDate);
        IntervalDto freeInterval2 = new IntervalDto(startDate, startDateRent1.minusSeconds(1));
    
        List<OrderItem> orderItems = Arrays.asList(
            new OrderItem(order, instrument, startDateRent1, endDateRent1, new BigDecimal(1000)),
            new OrderItem(order, instrument, startDateRent2, endDateRent2, new BigDecimal(2000))
        );
    
        doReturn(orderItems).when(orderItemService).findAllByInstrumentId(instrument.getId());
    
        InstrumentInfoDto instrumentInfoDto = instrumentConverter.toInstrumentInfoDto(instrument);
    
        assertEquals(2, instrumentInfoDto.getIntervals().size());
        assertEquals(freeInterval1, instrumentInfoDto.getIntervals().get(0));
        assertEquals(freeInterval2, instrumentInfoDto.getIntervals().get(1));
    }
    
}
