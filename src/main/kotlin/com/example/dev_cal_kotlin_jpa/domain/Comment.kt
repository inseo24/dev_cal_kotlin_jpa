package com.example.dev_cal_kotlin_jpa.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "comment")
class Comment(

    @Column(length = 100, nullable = false)
    var comment: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    @JsonIgnore
    val user: User,

    @JoinColumn(name = "board")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    val board: Board,

    ) : BaseEntity() {
}