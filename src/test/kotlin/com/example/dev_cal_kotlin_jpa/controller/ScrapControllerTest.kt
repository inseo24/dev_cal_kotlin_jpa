package com.example.dev_cal_kotlin_jpa.controller

import com.example.dev_cal_kotlin_jpa.dto.EventDto
import com.example.dev_cal_kotlin_jpa.dto.ScrapDto
import com.example.dev_cal_kotlin_jpa.dto.UserDto
import com.example.dev_cal_kotlin_jpa.responseDto.ResponseDto
import com.example.dev_cal_kotlin_jpa.service.ScrapService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post


import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.hamcrest.CoreMatchers.*
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.*
import java.time.LocalDateTime

@WebMvcTest(ScrapController::class)
internal class ScrapControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var scrapService: ScrapService

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    @DisplayName("scarp 로직 검증")
    fun scrap() {
        val startDate = LocalDateTime.of(2022, 1, 7, 11, 19)
        val endDate = LocalDateTime.of(2022, 1, 8, 19, 19)
        val event = EventDto(1L, "title 1", startDate, endDate, "uu", "60min", "1000", "100", "none")
        val user = UserDto("인서", "jnh100@naver.com", "123tjdls@", "010-2124-1281")
        val scrap = ScrapDto(event, user)

        `when`(scrapService.scrap(user.email, event.id!!)).thenReturn(ResponseEntity<HttpStatus>(HttpStatus.OK))

        val response: ResultActions = mockMvc.perform(post("/scrap/{email}/{eventId}", user.email, event.id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(scrap)))

        response.andDo(print())
            .andExpect(status().isOk)
    }

    @Test
    @DisplayName("delete scrap 로직 검증")
    fun deleteScrap() {
        val startDate = LocalDateTime.of(2022, 1, 7, 11, 19)
        val endDate = LocalDateTime.of(2022, 1, 8, 19, 19)
        val event = EventDto(1L, "title 1", startDate, endDate, "uu", "60min", "1000", "100", "none")
        val user = UserDto("인서", "jnh100@naver.com", "123tjdls@", "010-2124-1281")
        val scrap = ScrapDto(event, user)

        `when`(scrapService.scrap(user.email, event.id!!)).thenReturn(ResponseEntity<HttpStatus>(HttpStatus.OK))

        val response: ResultActions = mockMvc.perform(delete("/scrap/{email}/{eventId}", user.email, event.id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(scrap)))

        response.andDo(print())
            .andExpect(status().isOk)
    }

    @Test
    @DisplayName("findAll 로직 검증")
    fun findAll() {
        val startDate = LocalDateTime.of(2022, 1, 7, 11, 19)
        val endDate = LocalDateTime.of(2022, 1, 8, 19, 19)
        val event = EventDto(1L, "title 1", startDate, endDate, "uu", "60min", "1000", "100", "none")
        val user = UserDto("인서", "jnh100@naver.com", "123tjdls@", "010-2124-1281")
        val scrap = ScrapDto(event, user)
        val scrapList = listOf(scrap, ScrapDto(event, user))
        `when`(scrapService.findAll()).thenReturn(ResponseEntity<ResponseDto<ScrapDto>>(ResponseDto("200 OK",
            scrapList), HttpStatus.OK))

        val response: ResultActions = mockMvc.perform(get("/scrap"))

        response.andExpect(status().isOk)
            .andDo(print())
            .andExpect(jsonPath("$['data'].size()", `is`(scrapList.size)))
    }

    @Test
    @DisplayName("findAll User Scraps 로직 검증 ")
    fun findAllUserScraps() {
        val startDate = LocalDateTime.of(2022, 1, 7, 11, 19)
        val endDate = LocalDateTime.of(2022, 1, 8, 19, 19)
        val event = EventDto(1L, "title 1", startDate, endDate, "uu", "60min", "1000", "100", "none")
        val user = UserDto("인서", "jnh100@naver.com", "123tjdls@", "010-2124-1281")
        val scrap = ScrapDto(event, user)
        `when`(scrapService.findAllUsersScrap(user.email)).thenReturn(ResponseEntity<ResponseDto<ScrapDto>>(ResponseDto(
            "200 OK",
            scrap), HttpStatus.OK))

        val response: ResultActions = mockMvc.perform(get("/scrap/{email}", user.email))

        response.andExpect(status().isOk)
            .andDo(print())
            .andExpect(jsonPath("$['data']['event']['title']", `is`(event.title)))
            .andExpect(jsonPath("$['data']['user']['email']", `is`(user.email)))
    }
}