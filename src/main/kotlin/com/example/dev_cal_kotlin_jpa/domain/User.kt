package com.example.dev_cal_kotlin_jpa.domain

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

@Entity
@Table(name = "user")
data class User (

        var name : String,

        @Column(nullable = false, unique = true)
        var email : String,

        var password : String,

        @JsonProperty("mobile_number")
        var mobileNumber: String,

        @OneToMany(mappedBy = "user")
        var comments : MutableList<Comment> = mutableListOf(),

        @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
        var boards : MutableList<Board> = mutableListOf(),

        @OneToMany(mappedBy = "user")
        var events : MutableList<Event> = mutableListOf()

        ) : BaseEntity()