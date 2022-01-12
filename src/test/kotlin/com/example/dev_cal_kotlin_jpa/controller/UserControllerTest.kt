package com.example.dev_cal_kotlin_jpa.controller

import com.example.dev_cal_kotlin_jpa.dto.UserDto
import com.example.dev_cal_kotlin_jpa.responseDto.ResponseDto
import com.example.dev_cal_kotlin_jpa.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.*

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.hamcrest.CoreMatchers.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import kotlin.RuntimeException


@WebMvcTest(UserController::class)
internal class UserControllerTest {


    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var userService: UserService

    @Autowired
    lateinit var objectMapper: ObjectMapper


    @Test
    @DisplayName("user 계정 생성")
    fun create() {
        val request = UserDto("인서", "jnh100@naver.com", "123tjdls@", "010-2124-1281")

        `when`(userService.create(request)).thenAnswer { invocation ->
            ResponseEntity<ResponseDto<UserDto>>(ResponseDto<UserDto>("200 OK", invocation.arguments[0]), HttpStatus.OK)
        }

        val response: ResultActions = mockMvc.perform(post("/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))

        response.andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$['data']['email']", `is`(request.email)))
            .andExpect(jsonPath("$['data']['name']", `is`(request.name)))
            .andExpect(jsonPath("$['data']['password']", `is`(request.password)))
            .andExpect(jsonPath("$['data']['mobileNumber']", `is`(request.mobileNumber)))

    }

    @Test
    @DisplayName("user 정보 조회 - 성공")
    fun findOne() {
        val user = UserDto("인서", "jnh100@naver.com", "123tjdls@", "010-2124-1281")

        `when`(userService.findOne(user.email)).thenReturn(
            ResponseEntity<ResponseDto<UserDto>>(ResponseDto("200 OK", user), HttpStatus.OK))

        val response: ResultActions = mockMvc.perform(get("/user/email/{email}", user.email))

        response.andExpect(status().isOk)
            .andDo(print())
            .andExpect(jsonPath("$['data']['email']", `is`(user.email)))
            .andExpect(jsonPath("$['data']['name']", `is`(user.name)))
            .andExpect(jsonPath("$['data']['password']", `is`(user.password)))
            .andExpect(jsonPath("$['data']['mobileNumber']", `is`(user.mobileNumber)))
    }

    @Test
    @DisplayName("user 정보 조회 - 실패")
    fun findOneReturn400() {
        val user = UserDto("인서", "jnh100@naver.com", "123tjdls@", "010-2124-1281")

        `when`(userService.findOne(user.email)).thenThrow(RuntimeException()) // 이메일 조회 실패

        val response: ResultActions = mockMvc.perform(get("/user/email/{email}", user.email))

        response.andExpect(status().isBadRequest)
            .andDo(print())
    }

    @Test
    @DisplayName("user findAll")
    fun findAll() {
        val user = UserDto("인서", "jnh100@naver.com", "123tjdls@", "010-2124-1281")
        val userList: List<UserDto> = listOf(user, UserDto("서인", "jnh123@naver.com", "teas12@3", "010-9999-9999"))
        `when`(userService.findAll()).thenReturn(ResponseEntity<ResponseDto<UserDto>>(ResponseDto("200 OK", userList),
            HttpStatus.OK))

        val response: ResultActions = mockMvc.perform(get("/user"))

        response.andExpect(status().isOk)
            .andDo(print())
            .andExpect(jsonPath("$['data'].size()", `is`(userList.size)))
    }

    @Test
    @DisplayName("update 검증")
    fun update() {
        val originalUser = UserDto("인서", "jnh100@naver.com", "123tjdls@", "010-2124-1281")
        val updatedUser = UserDto("서인", "jnh123@naver.com", "teas12@3", "010-9999-9999")

        `when`(userService.update(originalUser)).thenAnswer {
            ResponseEntity<ResponseDto<UserDto>>(ResponseDto("200 OK", updatedUser), HttpStatus.OK)
        }

        val response: ResultActions = mockMvc.perform(put("/user/update")
            .content(objectMapper.writeValueAsString(originalUser))
            .contentType(MediaType.APPLICATION_JSON))

        response.andExpect(status().isOk)
            .andDo(print())
            .andExpect(jsonPath("$['data']['email']", `is`(updatedUser.email)))
            .andExpect(jsonPath("$['data']['name']", `is`(updatedUser.name)))
            .andExpect(jsonPath("$['data']['password']", `is`(updatedUser.password)))
            .andExpect(jsonPath("$['data']['mobileNumber']", `is`(updatedUser.mobileNumber)))
    }

    @Test
    @DisplayName("delete 검증")
    fun delete() {
        val email = "jnh3@naver.com"
        `when`(userService.delete(email)).thenReturn(ResponseEntity(HttpStatus.OK))

        val response: ResultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/{email}", email))

        response.andExpect(status().isOk)
            .andDo(print())
    }

}
