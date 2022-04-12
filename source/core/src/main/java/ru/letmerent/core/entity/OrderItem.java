package ru.letmerent.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "order_item")
@Entity
@NoArgsConstructor
@Getter
@Setter
@Check(constraints = "date_start < date_end")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date_start")
    private LocalDateTime dateStart;
    @Column(name = "date_finish")
    private LocalDateTime dateFinish;
    @ManyToOne
    private Order order;
    @ManyToOne
    private Instrument instrument;
}
