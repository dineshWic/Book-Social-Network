package com.dinesh.book.feedback;

import com.dinesh.book.book.Book;
import com.dinesh.book.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "feedback")
public class Feedback extends BaseEntity {

    @Column
    private Double note;

    @Column
    private String comment;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;


}
