package com.example.dev_cal_kotlin_jpa.domain

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "user")
data class User (

        val name : String,

        @Column(nullable = false, unique = true)
        val email : String,

        var password : String,

        @JsonProperty("mobile_number")
        var mobileNumber: String

        ) : BaseEntity()