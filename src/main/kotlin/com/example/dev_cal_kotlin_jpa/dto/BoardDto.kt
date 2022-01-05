package com.example.dev_cal_kotlin_jpa.dto

import javax.validation.constraints.NotBlank

data class BoardDto(

    val id: Long? = null,

    @field:NotBlank
    val title: String = "",

    @field:NotBlank
    val content: String = "",

    val user: UserDto? = null,

    )