package com.example.dev_cal_kotlin_jpa.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "scrap")
class Scrap(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event")
    @JsonIgnore
    var event: Event,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    val user: User,

    ) : BaseEntity()