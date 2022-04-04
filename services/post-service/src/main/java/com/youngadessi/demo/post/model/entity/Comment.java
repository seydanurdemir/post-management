package com.youngadessi.demo.post.model.entity;

import com.youngadessi.demo.model.BaseEntity;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tbl_post_comment")
public class Comment extends BaseEntity {

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "created_by_name")
    private String createdByName;

    @NotBlank
    @Column(name = "comment_text")
    private String commentText;

}
