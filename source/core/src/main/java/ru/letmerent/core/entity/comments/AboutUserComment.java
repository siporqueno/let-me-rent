package ru.letmerent.core.entity.comments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Date;

@Entity
@Data
@Table(name = "comments_about_users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AboutUserComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "user_user_id")
    private Long userId;

    @Column(name = "address_user_id")
    private Long aboutUserId;

    @Column(name = "description")
    private String description;

    @Column(name = "grade")
    private Integer grade;

    @Column(name = "start_date")
    @CreationTimestamp
    private Date startDate;

    @Column(name = "update_date")
    @UpdateTimestamp
    private Date updateDate;

    @Column(name = "end_date")
    @Temporal(value = TemporalType.DATE)
    @Generated(value = GenerationTime.INSERT)
    @ColumnDefault(value = "to_date('31.12.2999','DD.MM.YYYY')")
    private Date endDate;
}
