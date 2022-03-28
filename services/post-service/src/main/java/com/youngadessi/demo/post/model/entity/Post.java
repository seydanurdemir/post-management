package com.youngadessi.demo.post.model.entity;

import com.youngadessi.demo.model.BaseEntity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tbl_post")
public class Post extends BaseEntity {

    @Column(name = "created_by_name")
    private String createdByName;

    @Column(name = "content")
    private String content;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Comment> postComments;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tbl_post_tags",
            joinColumns = @JoinColumn(name = "post_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id",referencedColumnName = "id")
    )
    private List<Tag> postTags;

}
