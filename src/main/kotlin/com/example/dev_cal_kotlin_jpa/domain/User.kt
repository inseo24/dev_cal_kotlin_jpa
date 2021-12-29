package com.example.dev_cal_kotlin_jpa.domain

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "user")
data class User (

        var name : String?=null,

        @Column(nullable = false, unique = true)
        var email : String?=null,

        var password : String?=null,

        @JsonProperty("mobile_number")
        var mobileNumber: String?=null

        ) : BaseEntity()