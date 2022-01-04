package com.example.dev_cal_kotlin_jpa.domain

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table

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

        @OneToMany(mappedBy = "user")
        var boards : MutableList<Board> = mutableListOf(),

        @OneToMany(mappedBy = "user")
        var events : MutableList<Event> = mutableListOf()

        ) : BaseEntity()