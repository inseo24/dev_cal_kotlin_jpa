package com.example.dev_cal_kotlin_jpa.domain

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

@Entity
@Table(name = "user")
class User(

    var name: String,

    @Column(nullable = false, unique = true)
    var email: String,

    var password: String,

    @JsonProperty("mobile_number")
    var mobileNumber: String,


    ) : BaseEntity()