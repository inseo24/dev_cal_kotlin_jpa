package com.example.dev_cal_kotlin_jpa.controller

import com.example.dev_cal_kotlin_jpa.dto.EventDto
import com.example.dev_cal_kotlin_jpa.service.EventService
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@WebMvcTest(EventController::class)
internal class EventControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var eventService: EventService

    @Test
    @DisplayName("event findAll 로직 검증")
    fun findAll() {
        mockMvc.get("/event")
            .andExpect {
                status { isOk() }
            }
    }

    @Test
    @DisplayName("event create 로직 검증")
    fun create() {
        val startDate = LocalDateTime.of(2022, 1, 7, 11, 19)
        val endDate = LocalDateTime.of(2022, 1, 8, 19, 19)
        val request = EventDto(
            startDate = startDate,
            endDate = endDate,
            host = "uu",
            timeRequired = "60min",
            cost = "1000",
            limitPersonnel = "100",
            relatedLink = "none",
            title = "title 1"
        )

        mockMvc.post("/event") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().registerModule(JavaTimeModule()).writeValueAsString(request)
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    @DisplayName("event findAll events contains title 로직 검증")
    fun findAllEventsContainsTitle() {
        mockMvc.get("/event/title")
            .andExpect {
                status { isOk() }
            }
    }
}