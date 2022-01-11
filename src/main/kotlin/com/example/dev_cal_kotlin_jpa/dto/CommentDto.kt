package com.example.dev_cal_kotlin_jpa.dto

import javax.validation.constraints.NotBlank

data class CommentDto(

    val id: Long? = null,

    @field:NotBlank
    val user: UserDto? = null,

    @field:NotBlank
    var comment: String = "",

    @field:NotBlank
    val board: BoardDto? = null,

    )

