package ru.letmerent.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

@Table(name = "instrument")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Instrument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "fee")
    private BigDecimal fee;
    @ManyToOne
    private User owner;
    @OneToMany(mappedBy = "instrument", cascade = CascadeType.ALL)
    private List<Picture> pictures;
    @ManyToOne
    private Producer producer;
    @ManyToOne
    private Category category;
   @ManyToOne
    private List<OrderItem> orderItems;
}
