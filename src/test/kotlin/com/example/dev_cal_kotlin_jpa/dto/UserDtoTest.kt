package com.example.dev_cal_kotlin_jpa.dto

import org.junit.jupiter.api.Test
import javax.validation.Validation
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName


class UserDtoTest {

    val validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    @DisplayName("user Dto 검증 테스트")
    fun userDtoTest() {
        val userDto = UserDto(
            name = "서인",
            email = "jnh@naver.com",
            password = "1234@tjdls",
            mobileNumber = "010-1234-1281"
        )

        val userErrorDto = UserDto(
            name = "서인adfasdfsda",
            email = "jn=er.com",
            password = "12123jdls",
            mobileNumber = "031234-1281"
        )

        val userNullErrorDto = UserDto(
            name = "",
            email = "",
            password = "",
            mobileNumber = ""
        )

        val result = validator.validate(userDto)
        result.forEach {
            print(it.propertyPath.last().name + " : ")
            println(it.message)
            println(it.invalidValue)
        }
        println("==============================")
        val errorResult = validator.validate(userErrorDto)
        errorResult.forEach {
            print(it.propertyPath.last().name + " : ")
            println(it.message)
            println(it.invalidValue)
        }
        println("==============================")

        val nullResult = validator.validate(userNullErrorDto)
        nullResult.forEach {
            print(it.propertyPath.last().name + " : ")
            println(it.message)
            println(it.invalidValue)
        }
        println("==============================")

        assertThat(result.isEmpty()).isEqualTo(true)
        assertThat(errorResult.isEmpty()).isEqualTo(false)
        assertThat(nullResult.isEmpty()).isEqualTo(false)
    }
}