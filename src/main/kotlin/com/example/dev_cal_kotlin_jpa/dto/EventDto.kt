package com.example.dev_cal_kotlin_jpa.dto

import java.time.LocalDateTime
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class EventDto (

        var id : Long,

        @field:NotBlank
        var title : String="",

        var start : LocalDateTime,
        var end : LocalDateTime,

        var host : String="",
        var timeRequired : String="",
        var cost : String="",
        var limitPersonnel : String="",
        var relatedLink : String=""

)


