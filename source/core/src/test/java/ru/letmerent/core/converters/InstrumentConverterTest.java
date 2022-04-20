package ru.letmerent.core.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import ru.letmerent.core.dto.InstrumentInfoDto;
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
    private final Long id = 1L;
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
    
    private final LocalDateTime startDate = LocalDateTime.of(2022, 4, 20, 20, 30);
    private final LocalDateTime endDate = LocalDateTime.of(2022, 5, 20, 20, 30);
    
    @BeforeEach
    void init() {
        openMocks(this);
        
        ReflectionTestUtils.setField(instrumentConverter, "orderItemService", orderItemService);
        ReflectionTestUtils.setField(instrumentConverter, "categoryService", categoryService);
    }
    
    /**
     * При преобразовании инструмента в InstrumentDto, intervals инициализируются валидными данными.
     */
    @Test
    void instrumentDtoFromInstrument() {
        LocalDateTime startDate2 = LocalDateTime.of(2022, 4, 25, 20, 30);
        LocalDateTime endDate2 = LocalDateTime.of(2022, 5, 25, 20, 30);
        List<Picture> pictures = Collections.emptyList();
        Category category = new Category("test_name", "test_description", startDate, endDate);
        Instrument instrument = new Instrument(
            id,
            "test_title",
            "test_description",
            new BigDecimal(1000),
            new BigDecimal(100),
            startDate,
            endDate,
            id,
            user,
            pictures,
            brand
        );
        List<OrderItem> orderItems = Arrays.asList(
            new OrderItem(order, instrument, startDate, endDate, new BigDecimal(1000)),
            new OrderItem(order, instrument, startDate2, endDate2, new BigDecimal(2000))
        );
        
        doReturn(orderItems).when(orderItemService).findAllByInstrument(instrument);
        doReturn(category).when(categoryService).getCategoryById(instrument.getId());
        
        InstrumentInfoDto instrumentInfoDto = instrumentConverter.toInstrumentInfoDto(instrument);
        
        assertEquals(2, instrumentInfoDto.getIntervals().size());
        assertEquals(startDate, instrumentInfoDto.getIntervals().get(0).getDateStart());
        assertEquals(endDate, instrumentInfoDto.getIntervals().get(0).getDateFinish());
        assertEquals(startDate2, instrumentInfoDto.getIntervals().get(1).getDateStart());
        assertEquals(endDate2, instrumentInfoDto.getIntervals().get(1).getDateFinish());
    }
}
