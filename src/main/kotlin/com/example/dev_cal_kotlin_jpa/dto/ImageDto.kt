package com.example.dev_cal_kotlin_jpa.dto

import org.springframework.web.multipart.MultipartFile


data class ImageDto(
        val file: MultipartFile,
        val boardId: Long,
)


