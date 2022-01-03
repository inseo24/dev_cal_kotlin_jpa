package com.example.dev_cal_kotlin_jpa.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class BoardDto (

        var id : Long,
        @field:NotBlank
        var title : String="",

        @field:NotBlank
        var content : String="",

        var user : UserDto,
        var comments: MutableList<CommentDto>,
        var images: MutableList<ImageDto>

)


