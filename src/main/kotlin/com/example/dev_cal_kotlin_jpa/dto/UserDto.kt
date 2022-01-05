package com.example.dev_cal_kotlin_jpa.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class UserDto(

    @field:NotBlank
    @field:Size(min = 2, max = 10)
    val name: String = "",

    @field:NotBlank
    @field:Email
    val email: String = "",

    @field:NotBlank
    @field:Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
        message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    val password: String = "",

    @field:NotBlank
    @field:Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$",
        message = "010-1234-2133 형식에 맞게 작성해주세요.")
    val mobileNumber: String = "",
)


