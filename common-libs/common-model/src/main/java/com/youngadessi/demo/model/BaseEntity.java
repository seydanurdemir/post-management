package com.youngadessi.demo.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedBy;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
//@Builder
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @CreatedBy
    @Column(name = "created_by")
    private Long createdBy;

    @CreationTimestamp
    @Column(name = "created_date_time")
    private LocalDateTime createdDateTime;

    @LastModifiedBy
    @Column(name = "updated_by")
    private Long updatedBy;

    @UpdateTimestamp
    @Column(name = " updated_date_time")
    private LocalDateTime updatedDateTime;

    @Version
    @Column(name = "version")
    private  Integer version=0;

}
