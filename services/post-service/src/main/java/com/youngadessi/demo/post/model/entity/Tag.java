package com.youngadessi.demo.post.model.entity;

import com.youngadessi.demo.model.BaseEntity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tbl_tag")
public class Tag extends BaseEntity {

    @Column(name = "tag_name")
    private String tagName;

}
