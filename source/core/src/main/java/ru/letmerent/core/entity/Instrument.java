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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
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
    @Column(name = "brand")
    private String brand;
    @Column(name = "description")
    private String description;
    @Column(name = "fee")
    private BigDecimal fee;
    @ManyToOne
    private User owner;
    @OneToMany(mappedBy = "instrument", cascade = CascadeType.ALL)
    private List<Picture> pictures = new ArrayList<>();
    
    public Instrument(String title, String brand, String description, BigDecimal fee, User owner, List<Picture> pictures) {
        this.title = title;
        this.brand = brand;
        this.description = description;
        this.fee = fee;
        this.owner = owner;
        this.pictures = pictures;
    }
}
