package ru.letmerent.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

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

@Table(name = "instruments")
@Entity
@NoArgsConstructor
@Data
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

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "category")
    private Long categoryId;

    @ManyToOne
    @JoinColumn(name = "owner")
    private User user;

    @OneToMany(mappedBy = "instrument", cascade = CascadeType.ALL)
    private Collection<Picture> pictures;

    @ManyToOne
    @JoinColumn(name = "brand_brand_id")
    private Brand brand;

}
