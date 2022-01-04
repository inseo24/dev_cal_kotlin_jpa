package com.example.dev_cal_kotlin_jpa.dto

import javax.validation.constraints.NotBlank

data class CommentDto (

        var id : Long?=null,

        @field:NotBlank
        var user : UserDto?=null,

        @field:NotBlank
        var comment : String=""

){

        init{
                user = UserDto().apply {
                        name = "$name"
                        email = "$email"
                }

        }
}



