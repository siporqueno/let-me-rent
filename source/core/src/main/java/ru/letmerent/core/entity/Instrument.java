package ru.letmerent.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Table(name = "instruments")
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
    private Collection<Picture> pictures = new ArrayList<>();
    
    @OneToMany(mappedBy = "instrument", cascade = CascadeType.ALL)
    private Collection<OrderItem> orderItems;
    
    @ManyToOne
    @JoinColumn(name = "brand_brand_id")
    private Brand brand;
    
    public Instrument(
        String title,
        String description,
        BigDecimal price,
        BigDecimal fee,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Long categoryId,
        User user,
        Collection<Picture> pictures,
        Brand brand
    ) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.fee = fee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.categoryId = categoryId;
        this.user = user;
        this.pictures.addAll(pictures);
        this.brand = brand;
    }
}
