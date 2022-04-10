package ru.letmerent.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "producer")
@NoArgsConstructor
@Getter
@Setter
public class Producer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @Column(name = "description", unique = true, nullable = false)
    private String description;
    @OneToMany(mappedBy = "category")
    private List<Instrument> instruments;
    
    public Producer(String name, String description, List<Instrument> instruments) {
        this.name = name;
        this.description = description;
        this.instruments = instruments;
    }
}
