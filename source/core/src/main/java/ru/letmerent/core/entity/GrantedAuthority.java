package ru.letmerent.core.entity;

import lombok.Builder;
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
import java.time.LocalDateTime;

@Table(name = "granted_authorities")
@Entity
@Data
@NoArgsConstructor
public class GrantedAuthority {

    @Id
    @Column(name = "grant_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long grantId;

    @ManyToOne
    @JoinColumn(name = "user_user_id")
    private User user;

    @Column(name = "role_role_id")
    private Long roleId;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;
}
