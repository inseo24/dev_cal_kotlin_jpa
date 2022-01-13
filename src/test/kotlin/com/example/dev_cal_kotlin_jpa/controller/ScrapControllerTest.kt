package com.example.dev_cal_kotlin_jpa.controller

import com.example.dev_cal_kotlin_jpa.domain.Scrap
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

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.hamcrest.CoreMatchers.*
import org.junit.jupiter.api.BeforeEach
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

    lateinit var eventDto: EventDto

    lateinit var userDto: UserDto

    lateinit var scrapDto: ScrapDto

    @BeforeEach
    fun setup() {
        userDto = UserDto("seoin", "jnh123@naver.com", "1234@tjdls", "010-1234-1231")
        eventDto = EventDto(
            1L, "title 1",
            LocalDateTime.of(2022, 1, 7, 11, 19),
            LocalDateTime.of(2022, 1, 8, 19, 19),
            "host 1",
            "60",
            "1000",
            "100",
            "none",
        )
        scrapDto = ScrapDto(eventDto,userDto)
    }

    @Test
    @DisplayName("scarp 로직 검증")
    fun scrap() {
        `when`(scrapService.scrap(userDto.email, eventDto.id!!)).thenReturn(ResponseEntity<HttpStatus>(HttpStatus.OK))

        val response: ResultActions = mockMvc.perform(post("/scrap/{email}/{eventId}", userDto.email, eventDto.id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(scrapDto)))

        response.andDo(print())
            .andExpect(status().isOk)
    }

    @Test
    @DisplayName("delete scrap 로직 검증")
    fun deleteScrap() {
        `when`(scrapService.scrap(userDto.email, eventDto.id!!)).thenReturn(ResponseEntity<HttpStatus>(HttpStatus.OK))

        val response: ResultActions = mockMvc.perform(delete("/scrap/{email}/{eventId}", userDto.email, eventDto.id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(scrapDto)))

        response.andDo(print())
            .andExpect(status().isOk)
    }

    @Test
    @DisplayName("findAll 로직 검증")
    fun findAll() {
        val scrapList = listOf(scrapDto, ScrapDto(eventDto, userDto))
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
        `when`(scrapService.findAllUsersScrap(userDto.email)).thenReturn(ResponseEntity<ResponseDto<ScrapDto>>(ResponseDto(
            "200 OK",
            scrapDto), HttpStatus.OK))

        val response: ResultActions = mockMvc.perform(get("/scrap/{email}", userDto.email))

        response.andExpect(status().isOk)
            .andDo(print())
            .andExpect(jsonPath("$['data']['event']['title']", `is`(eventDto.title)))
            .andExpect(jsonPath("$['data']['user']['email']", `is`(userDto.email)))
    }
}