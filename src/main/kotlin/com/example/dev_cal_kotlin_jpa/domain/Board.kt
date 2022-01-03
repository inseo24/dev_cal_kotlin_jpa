package com.example.dev_cal_kotlin_jpa.domain

import javax.persistence.*


@Entity
@Table(name = "board")
data class Board (


        @ManyToOne
        @JoinColumn(name = "user")
        val user : User,

        @OneToMany(cascade = [CascadeType.REMOVE], mappedBy = "board")
        var images : MutableList<Image> = mutableListOf(),

        @OneToMany(cascade = [CascadeType.REMOVE], mappedBy = "board")
        var comments : MutableList<Comment> = mutableListOf(),

        var title : String,
        var content : String,


        ) : BaseEntity()