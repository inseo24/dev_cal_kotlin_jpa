package com.example.dev_cal_kotlin_jpa.dto

import java.time.LocalDateTime
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

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


