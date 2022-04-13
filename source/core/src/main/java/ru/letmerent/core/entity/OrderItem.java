package ru.letmerent.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "order_items")
@Entity
@NoArgsConstructor
@Getter
@Setter
@Check(constraints = "date_start < date_end")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "instr_instr_id")
    private Instrument instrument;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "rent_price")
    private BigDecimal rentPrice;

}
