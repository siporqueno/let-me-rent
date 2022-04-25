package ru.letmerent.core.repositories.specifications.impl;

import static java.util.stream.Collectors.toSet;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.letmerent.core.dto.CriteriaSearch;
import ru.letmerent.core.entity.Category;
import ru.letmerent.core.entity.Instrument;
import ru.letmerent.core.entity.OrderItem;
import ru.letmerent.core.repositories.CategoryRepository;
import ru.letmerent.core.repositories.InstrumentRepository;
import ru.letmerent.core.repositories.OrderItemRepository;
import ru.letmerent.core.repositories.specifications.InstrumentSpecification;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class InstrumentSpecificationImpl implements InstrumentSpecification {

    private final CategoryRepository categoryRepository;

    private final OrderItemRepository orderItemRepository;

    private final InstrumentRepository instrumentRepository;

    @Transactional
    @Override
    public Specification<Instrument> spec(CriteriaSearch criteriaSearch) {
        Specification<Instrument> spec = Specification.where(null);

        if (criteriaSearch == null) {
            return spec;
        }

        Set<Long> categoryIds = categoryRepository.findAll(specForCategoryName(criteriaSearch))
                .stream()
                .map(Category::getId)
                .collect(toSet());

        Set<Long> validIds = instrumentRepository.findAll().stream()
                .map(Instrument::getId)
                .filter(id -> !orderItemRepository.findAll().stream()
                        .filter(orderItem -> !checkDate(orderItem, criteriaSearch.getStartDate(), criteriaSearch.getEndDate()))
                        .map(OrderItem::getInstrument)
                        .map(Instrument::getId)
                        .collect(toSet()).contains(id))
                .collect(toSet());


        return spec.and(specLike("title", criteriaSearch.getTitle()))
                .and(specLikeForUser("userName", criteriaSearch.getOwnerName()))
                .and(specForPrice("fee", criteriaSearch.getMaxFee()))
                .and(specIn("categoryId", categoryIds))
                .and(specIn("id", validIds));
    }

    private Specification<Instrument> specIn(String key, Set<Long> value) {
        return (root, query, criteriaBuilder) -> root.get(key).in(value);
    }

    private Specification<Instrument> specLike(String key, String value) {
        return (root, query, criteriaBuilder) -> {
            if (value == null) {
                return null;
            }
            return criteriaBuilder.like(root.get(key), "%" + value + "%");
        };
    }

    private Specification<Instrument> specLikeForUser(String key, String value) {
        return (root, query, criteriaBuilder) -> {
            if (value == null) {
                return null;
            }
            return criteriaBuilder.like(root.get("user").get(key), "%" + value + "%");
        };
    }

    private Specification<Instrument> specForPrice(String key, BigDecimal value) {
        return (root, query, criteriaBuilder) -> {
            if (value == null) {
                return null;
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get(key), value);
        };
    }

    private boolean checkCategory(Category category, String nameCategory) {
        if (nameCategory == null) {
            return true;
        }
        return category.getName().contains(nameCategory);
    }

    private boolean checkDate(OrderItem orderItem, String startDate, String endDate) {
        if (startDate == null && endDate == null) {
            return true;
        }
        LocalDateTime currentDate = LocalDateTime.now();

        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("yyyy-MMMM-dd")
                .toFormatter(Locale.US);

        if (startDate == null) {
            if (!endDate.equals("")) {
                var end = LocalDate.parse(endDate, formatter).atTime(0, 0, 0);
                return (end.isAfter(orderItem.getEndDate()) || end.isBefore(orderItem.getStartDate()) ||
                        end.equals(orderItem.getEndDate()) || end.equals(orderItem.getStartDate())) &&
                        end.isAfter(currentDate);
            } else {
                return true;
            }
        }

        if (endDate == null) {
            if (!startDate.equals("")) {
                var start = LocalDate.parse(startDate, formatter).atTime(0, 0, 0);
                return (start.isAfter(orderItem.getEndDate()) || start.isBefore(orderItem.getStartDate()) ||
                        start.equals(orderItem.getEndDate()) || start.equals(orderItem.getStartDate())) &&
                        (start.isAfter(currentDate) || start.equals(currentDate));
            } else {
                return true;
            }
        }

        if (startDate.equals("") && endDate.equals("")) {
            return true;
        }

        var start = LocalDate.parse(startDate, formatter).atTime(0, 0, 0);
        var end = LocalDate.parse(endDate, formatter).atTime(0, 0, 0);
        return (((start.isAfter(orderItem.getEndDate()) || start.equals(orderItem.getEndDate())) &&
                end.isAfter(orderItem.getEndDate())) ||
                (((end.isBefore(orderItem.getStartDate()) || end.equals(orderItem.getStartDate()))) &&
                        start.isBefore(orderItem.getStartDate()))) &&
                (start.isAfter(currentDate) || start.equals(currentDate)) &&
                end.isAfter(currentDate);
    }

    private Specification<Category> specForCategoryName(CriteriaSearch criteriaSearch) {
        Specification<Category> spec = Specification.where(null);
        if (criteriaSearch.getCategoryName() == null) {
            return spec;
        }
        return spec.and((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + criteriaSearch.getCategoryName() + "%"));
    }
}