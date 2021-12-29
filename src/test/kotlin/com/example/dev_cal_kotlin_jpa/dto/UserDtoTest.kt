package com.example.dev_cal_kotlin_jpa.dto

import org.junit.jupiter.api.Test
import javax.validation.Validation
import org.assertj.core.api.Assertions.*


class UserDtoTest {

    val validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun userDtoTest() {
        val userDto = UserDto(
                name = "서인",
                email = "jnh@naver.com",
                password = "1234=ls",
                mobileNumber = "010-1234-1281"
        )

        val result = validator.validate(userDto)

        result.forEach {
            print(it.propertyPath.last().name + " : ")
            println(it.message)
            println(it.invalidValue)
        }

        assertThat(result.isEmpty()).isEqualTo(true)

    }
}