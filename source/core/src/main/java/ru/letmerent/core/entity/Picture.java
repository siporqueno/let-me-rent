package ru.letmerent.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "picture")
@NoArgsConstructor
@Getter
@Setter
public class Picture {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "content_type", nullable = false)
    private String contentType;
    
    @Column(name = "storage_uuid")
    private String storageUUID;
    
    @ManyToOne
    private Instrument instrument;
    
    public Picture(String name, String contentType, String storageUUID, Instrument instrument) {
        this.name = name;
        this.contentType = contentType;
        this.storageUUID = storageUUID;
        this.instrument = instrument;
    }
}
