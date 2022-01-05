package com.example.dev_cal_kotlin_jpa.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*


@Entity
@Table(name = "board")
class Board(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    var user: User,

    @JsonIgnore
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "board", fetch = FetchType.LAZY)
    var images: MutableList<Image> = mutableListOf(),

    @JsonIgnore
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "board", fetch = FetchType.LAZY)
    var comments: MutableList<Comment> = mutableListOf(),

    var title: String,
    var content: String,

    ) : BaseEntity()