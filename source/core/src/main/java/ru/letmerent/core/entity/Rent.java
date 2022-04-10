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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

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
    @ManyToOne
    private User lessor;
    
    @Column(name = "date_start")
    private LocalDateTime dateStart;
    @Column(name = "date_end")
    private LocalDateTime dateEnd;
    
    public Rent(User renter, User lessor, LocalDateTime dateStart, LocalDateTime dateEnd) {
        this.renter = renter;
        this.lessor = lessor;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }
}
