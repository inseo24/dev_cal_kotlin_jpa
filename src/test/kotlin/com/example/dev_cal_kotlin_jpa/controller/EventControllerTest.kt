package com.example.dev_cal_kotlin_jpa.controller

import com.example.dev_cal_kotlin_jpa.dto.EventDto
import com.example.dev_cal_kotlin_jpa.responseDto.ResponseDto
import com.example.dev_cal_kotlin_jpa.service.EventService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import java.time.LocalDateTime

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.hamcrest.CoreMatchers.*
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.*

@WebMvcTest(EventController::class)
internal class EventControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var eventService: EventService

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    @DisplayName("event findAll 로직 검증")
    fun findAll() {
        val startDate = LocalDateTime.of(2022, 1, 7, 11, 19)
        val endDate = LocalDateTime.of(2022, 1, 8, 19, 19)
        val event = EventDto(1L, "title 1", startDate, endDate, "uu", "60min", "1000", "100", "none")
        val eventList: List<EventDto> =
            listOf(event, EventDto(2L, "title 2", startDate, endDate, "uu", "60min", "1000", "100", "none"))

        `when`(eventService.findAll()).thenReturn(ResponseEntity<ResponseDto<EventDto>>(ResponseDto("200 OK",
            eventList), HttpStatus.OK))

        val response: ResultActions = mockMvc.perform(get("/event"))

        response.andExpect(status().isOk)
            .andDo(print())
            .andExpect(jsonPath("$['data'].size()", `is`(eventList.size)))
    }

    @Test
    @DisplayName("event create 로직 검증")
    fun create() {
        val startDate = LocalDateTime.of(2022, 1, 7, 11, 19)
        val endDate = LocalDateTime.of(2022, 1, 8, 19, 19)
        val event = EventDto(1L, "title 1", startDate, endDate, "uu", "60min", "1000", "100", "none")
        `when`(eventService.create(event)).thenAnswer { invocation ->
            ResponseEntity<ResponseDto<EventDto>>(ResponseDto("200 OK", invocation.arguments[0]), HttpStatus.OK)
        }

        val response: ResultActions = mockMvc.perform(post("/event")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(event)))

        response.andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$['data']['timeRequired']", `is`(event.timeRequired)))
            .andExpect(jsonPath("$['data']['title']", `is`(event.title)))
            .andExpect(jsonPath("$['data']['cost']", `is`(event.cost)))
            .andExpect(jsonPath("$['data']['limitPersonnel']", `is`(event.limitPersonnel)))
    }

    @Test
    @DisplayName("event findAll events contains title 로직 검증")
    fun findAllEventsContainsTitle() {
        val startDate = LocalDateTime.of(2022, 1, 7, 11, 19)
        val endDate = LocalDateTime.of(2022, 1, 8, 19, 19)
        val event = EventDto(1L, "title", startDate, endDate, "uu", "60min", "1000", "100", "none")
        val eventList: List<EventDto> =
            listOf(event, EventDto(2L, "title", startDate, endDate, "uu", "60min", "1000", "100", "none"))

        `when`(eventService.findAllEventsContainsTitle(event.title)).thenReturn(ResponseEntity<ResponseDto<EventDto>>(
            ResponseDto("200 OK",
                eventList),
            HttpStatus.OK))

        val response: ResultActions = mockMvc.perform(get("/event/title"))

        response.andExpect(status().isOk)
            .andDo(print())
            .andExpect(jsonPath("$['data'].size()", `is`(eventList.size)))
    }
}