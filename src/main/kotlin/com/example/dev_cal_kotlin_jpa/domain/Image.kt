package com.example.dev_cal_kotlin_jpa.domain

import javax.persistence.*

@Entity
@Table(name = "image")
data class Image (
        var name: String,
        var type: String,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "board")
        var board: Board

        ) : BaseEntity()