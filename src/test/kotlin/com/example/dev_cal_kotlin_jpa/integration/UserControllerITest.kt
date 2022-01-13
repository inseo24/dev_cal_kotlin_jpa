package com.example.dev_cal_kotlin_jpa.integration

import com.example.dev_cal_kotlin_jpa.domain.User
import com.example.dev_cal_kotlin_jpa.dto.UserDto
import com.example.dev_cal_kotlin_jpa.persistence.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.hamcrest.CoreMatchers.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal class UserControllerITest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var objectMapper: ObjectMapper

    lateinit var user: User

    @BeforeEach
    fun setup() {
        userRepository.deleteAll()
        user = User("인서", "jnh57@naver.com", "123tjdls@", "010-2124-1281")
    }

    @Test
    @DisplayName("user 계정 생성")
    fun create() {
        val userDto = UserDto("인서", "jnh57@naver.com", "123tjdls@", "010-2124-1281")
        val response: ResultActions = mockMvc.perform(post("/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userDto)))

        response.andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$['data']['email']", `is`(userDto.email)))
            .andExpect(jsonPath("$['data']['name']", `is`(userDto.name)))
            .andExpect(jsonPath("$['data']['password']", `is`(userDto.password)))
            .andExpect(jsonPath("$['data']['mobileNumber']", `is`(userDto.mobileNumber)))
    }

    @Test
    @DisplayName("user 정보 조회 - 성공")
    fun findOneReturn200() {
        val savedUser = userRepository.save(user)
        val response: ResultActions = mockMvc.perform(get("/user/email/{email}", savedUser.email))

        response.andExpect(status().isOk)
            .andDo(print())
            .andExpect(jsonPath("$['data']['email']", `is`(savedUser.email)))
            .andExpect(jsonPath("$['data']['name']", `is`(savedUser.name)))
            .andExpect(jsonPath("$['data']['password']", `is`(savedUser.password)))
            .andExpect(jsonPath("$['data']['mobileNumber']", `is`(savedUser.mobileNumber)))
    }

    @Test
    @DisplayName("user 정보 조회 - 실패")
    fun findOneReturn400() {
        val response: ResultActions = mockMvc.perform(get("/user/email/{email}", user.email))

        response.andExpect(status().isBadRequest)
            .andDo(print())
    }

    @Test
    @DisplayName("user findAll")
    fun findAll() {
        val userList: List<User> = listOf(user, User("서인", "jnh123@naver.com", "teas12@3", "010-9999-9999"))
        userRepository.saveAll(userList)
        val response: ResultActions = mockMvc.perform(get("/user"))

        response.andExpect(status().isOk)
            .andDo(print())
            .andExpect(jsonPath("$['data'].size()", `is`(userList.size)))
    }

    @Test
    @DisplayName("update 검증")
    fun update() {
        userRepository.save(user)
        val updatedUser = UserDto("인서", "jnh57@naver.com", "123123!2n", "010-1111-1111")

        val response: ResultActions = mockMvc.perform(put("/user/update")
            .content(objectMapper.writeValueAsString(updatedUser))
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
        val savedUser = userRepository.save(user)
        val response: ResultActions = mockMvc.perform(delete("/user/delete/{email}", savedUser.email))

        response.andExpect(status().isOk)
            .andDo(print())
    }
}
