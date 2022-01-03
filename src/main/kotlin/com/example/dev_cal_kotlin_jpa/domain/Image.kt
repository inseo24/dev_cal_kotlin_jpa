package com.example.dev_cal_kotlin_jpa.domain

import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "image")
data class Image (
        var name: String,
        var type: String,

        @ManyToOne
        @JoinColumn(name = "board")
        var board: Board

        ) : BaseEntity()