package com.example.dev_cal_kotlin_jpa.domain

import javax.persistence.*

@Entity
@Table(name = "image")
class Image(
    val name: String,
    val type: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board")
    val board: Board,

    ) : BaseEntity()