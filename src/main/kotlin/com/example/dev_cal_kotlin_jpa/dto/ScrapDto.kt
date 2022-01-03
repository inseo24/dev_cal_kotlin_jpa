package com.example.dev_cal_kotlin_jpa.dto

import com.example.dev_cal_kotlin_jpa.domain.Event
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

// scrap id 를 넣어줄 필요가 있을까?
data class ScrapDto (

        var eventDto: EventDto,
        var userDto: UserDto

)


