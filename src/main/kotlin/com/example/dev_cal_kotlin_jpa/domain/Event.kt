package com.example.dev_cal_kotlin_jpa.domain

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "event")
class Event(

    val title: String,

    @JsonProperty("start_date")
    val startDate: LocalDateTime,

    @JsonProperty("end_date")
    val endDate: LocalDateTime,

    val host: String,

    @JsonProperty("time_required")
    val timeRequired: String,

    val cost: String,

    @JsonProperty("limit_personnel")
    val limitPersonnel: String,

    @JsonProperty("related_link")
    val relatedLink: String,

    ) : BaseEntity()