package com.example.dev_cal_kotlin_jpa.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "scrap")
class Scrap(

    @ManyToOne
    @JoinColumn(name = "event")
    @JsonIgnore
    val event: Event,

    @ManyToOne
    @JoinColumn(name = "user")
    val user: User,

    ) : BaseEntity()