package com.example.dev_cal_kotlin_jpa.domain

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "event")
data class Event (

        var title : String,
        var start : LocalDateTime,
        var end : LocalDateTime,

        var host : String,

        @JsonProperty("time_required")
        var timeRequired : String,

        var cost : String,

        @JsonProperty("limit_personnel")
        var limitPersonnel : String,

        @JsonProperty("related_link")
        var relatedLink : String,

        @ManyToOne
        @JoinColumn(name = "user")
        var user: User



        ) : BaseEntity()