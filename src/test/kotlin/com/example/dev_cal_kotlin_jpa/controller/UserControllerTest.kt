package com.example.dev_cal_kotlin_jpa.controller

import com.example.dev_cal_kotlin_jpa.dto.UserDto
import com.example.dev_cal_kotlin_jpa.service.UserService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.*


// create, update 할 때 넘겨주는 dto가 service 쪽으로 알아서 넘아가서 잘 매퍼로 변환해서 어쩌구 하는 상황이 제대로 안되는 거 같음
// 이것도 mock 이라 그런건가

// JPA meta model error
@ExtendWith(SpringExtension::class)
@WebMvcTest(UserController::class)
@ActiveProfiles("test")
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
            }.andExpect {
                content { MediaType.APPLICATION_JSON }
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
