package com.example.dev_cal_kotlin_jpa.dto

import javax.validation.constraints.NotBlank

data class BoardDto (

        var id : Long?=null,

        @field:NotBlank
        var title : String ="",

        @field:NotBlank
        var content : String="",

        var user : UserDto?=null,
        var comments: MutableList<CommentDto> = mutableListOf(),
        var images: MutableList<ImageDto> = mutableListOf()

)
{

        init{
                user = UserDto().apply {
                        name = "$name"
                        email = "$email"
                }

        }

}


