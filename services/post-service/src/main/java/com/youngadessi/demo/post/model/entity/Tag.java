package com.youngadessi.demo.post.model.entity;

import com.youngadessi.demo.model.BaseEntity;


import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tbl_tag")
public class Tag extends BaseEntity {

    @NotBlank
    @Column(name = "tag_name")
    private String tagName;

}
