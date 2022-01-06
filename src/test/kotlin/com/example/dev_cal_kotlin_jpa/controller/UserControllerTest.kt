package com.example.dev_cal_kotlin_jpa.controller

import com.example.dev_cal_kotlin_jpa.dto.UserDto
import com.example.dev_cal_kotlin_jpa.service.UserService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.*


@ExtendWith(SpringExtension::class)
@WebMvcTest(UserController::class)
internal class UserControllerTest {


    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var userService: UserService


    @Test
    @DisplayName("user 계정 생성")
    fun create() {
        val request = UserDto(
            "인서",
            "jnh100@naver.com",
            "123tjdls@",
            "010-2124-1281"
        )


        mockMvc.post("/user") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(request)
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    @DisplayName("user 정보 조회")
    fun findOne() {
        mockMvc.get("/user/email/jnh5@naver.com")
            .andExpect {
                status { isOk() }
            }
    }

    @Test
    fun findAll() {
        mockMvc.get("/user")
            .andExpect {
                status { isOk() }
            }.andExpect {
                content { MediaType.APPLICATION_JSON }
            }
    }

    @Test
    fun update() {
        val request = UserDto(
            "인서",
            "jnh100@naver.com",
            "123tjdls@",
            "010-2124-1281"
        )

        mockMvc.put("/user/update") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(request)
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    fun delete() {
        mockMvc.delete("/user/delete/jnh5@naver.com")
            .andExpect {
                status { isOk() }
            }
    }

}
