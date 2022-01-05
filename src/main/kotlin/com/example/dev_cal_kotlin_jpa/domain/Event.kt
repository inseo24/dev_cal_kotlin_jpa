package com.example.dev_cal_kotlin_jpa.domain

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "event")
data class Event (

        var title : String,

        @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
        var start : LocalDateTime,

        @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
        var end : LocalDateTime,

        var host : String,

        @JsonProperty("time_required")
        var timeRequired : String,

        var cost : String,

        @JsonProperty("limit_personnel")
        var limitPersonnel : String,

        @JsonProperty("related_link")
        var relatedLink : String,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user")
        var user: User

        ) : BaseEntity()