package com.example.dev_cal_kotlin_jpa.responseDto

data class ResponseDto<E>(
    var status: String? = null,
    var data: Any? = null,
)