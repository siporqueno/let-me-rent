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
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "rent")
@Entity
@NoArgsConstructor
@Getter
@Setter
@Check(constraints = "date_start < date_end")
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User renter;
    @Column(name = "date_start")
    private LocalDateTime dateStart;
    @Column(name = "date_end")
    private LocalDateTime dateEnd;
    @ManyToMany(mappedBy = "rents")
    private List<Instrument> instruments;
    
    public Rent(User renter, LocalDateTime dateStart, LocalDateTime dateEnd, List<Instrument> instruments) {
        this.renter = renter;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.instruments = instruments;
    }
}
