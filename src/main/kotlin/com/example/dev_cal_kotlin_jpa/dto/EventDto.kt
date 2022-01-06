package com.example.dev_cal_kotlin_jpa.dto

import java.time.LocalDateTime
import javax.validation.constraints.NotBlank

data class EventDto(

    val id: Long? = null,

    @field:NotBlank
    val title: String = "",

    val startDate: LocalDateTime? = null,
    val endDate: LocalDateTime? = null,

    val host: String = "",
    val timeRequired: String = "",
    val cost: String = "",
    val limitPersonnel: String = "",
    val relatedLink: String = "",

    )


