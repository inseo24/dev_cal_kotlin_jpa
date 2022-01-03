package com.example.dev_cal_kotlin_jpa.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class CommentDto (

        var id : Long,

        @field:NotBlank
        var user : UserDto,

        @field:NotBlank
        var comment : String="",

        @field:NotBlank
        var content : String="",

)


