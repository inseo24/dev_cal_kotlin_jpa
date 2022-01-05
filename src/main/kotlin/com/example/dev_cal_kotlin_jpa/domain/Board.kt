package com.example.dev_cal_kotlin_jpa.domain

import javax.persistence.*


@Entity
@Table(name = "board")
class Board(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    var user: User,

    var title: String,
    var content: String,

    ) : BaseEntity()