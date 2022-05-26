package ru.letmerent.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;

@Table(name = "orders")
@Entity
@NoArgsConstructor
@Data
@Check(constraints = "date_start < date_end")
public class Order implements IOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "renter")
    private User user;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "total_rent_price")
    private BigDecimal rentTotalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    Collection<OrderItem> orderItems;
}
