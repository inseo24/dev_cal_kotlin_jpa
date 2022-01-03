package com.example.dev_cal_kotlin_jpa.dto

import org.springframework.web.multipart.MultipartFile
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class ImageDto (
        var file : MultipartFile,
        var boardId : Long
)


