package ru.letmerent.core.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.letmerent.core.dto.CriteriaSearch;
import ru.letmerent.core.entity.Instrument;

public interface InstrumentSpecification {

    Specification<Instrument> spec(CriteriaSearch criteriaSearch);
}
