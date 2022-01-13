package com.example.dev_cal_kotlin_jpa.integration

import com.example.dev_cal_kotlin_jpa.domain.Event
import com.example.dev_cal_kotlin_jpa.domain.User
import com.example.dev_cal_kotlin_jpa.dto.EventDto
import com.example.dev_cal_kotlin_jpa.persistence.EventRepository
import com.example.dev_cal_kotlin_jpa.persistence.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import java.time.LocalDateTime

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.hamcrest.CoreMatchers.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal class EventControllerITest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var eventRepository: EventRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var objectMapper: ObjectMapper

    lateinit var eventDto: EventDto
    lateinit var event: Event
    lateinit var user: User

    @BeforeEach
    fun setup() {
        eventRepository.deleteAll()
        userRepository.deleteAll()

        user = User("인서", "jnh57@naver.com", "123tjdls@", "010-2124-1281")
        event = Event("title",
            LocalDateTime.of(2022, 1, 7, 11, 19),
            LocalDateTime.of(2022, 1, 8, 19, 19),
            "host 1",
            "60",
            "1000",
            "100",
            "none", user)

        userRepository.save(user)

        eventDto = EventDto(
            1L, "title",
            LocalDateTime.of(2022, 1, 7, 11, 19),
            LocalDateTime.of(2022, 1, 8, 19, 19),
            "host 1",
            "60",
            "1000",
            "100",
            "none",
        )
    }


    @Test
    @DisplayName("event findAll 로직 검증")
    fun findAll() {
        val eventList: List<Event> =
            listOf(event, Event("title 2", LocalDateTime.of(2022, 1, 7, 11, 19),
                LocalDateTime.of(2022, 1, 8, 19, 19),
                "uu", "60min", "1000", "100", "none", user))
        eventRepository.saveAll(eventList)
        val response: ResultActions = mockMvc.perform(get("/event"))

        response.andExpect(status().isOk)
            .andDo(print())
            .andExpect(jsonPath("$['data'].size()", `is`(eventList.size)))
    }

    @Test
    @DisplayName("event create 로직 검증")
    fun create() {
        val response: ResultActions = mockMvc.perform(post("/event")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(eventDto)))

        response.andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$['data']['timeRequired']", `is`(eventDto.timeRequired)))
            .andExpect(jsonPath("$['data']['title']", `is`(eventDto.title)))
            .andExpect(jsonPath("$['data']['cost']", `is`(eventDto.cost)))
            .andExpect(jsonPath("$['data']['limitPersonnel']", `is`(eventDto.limitPersonnel)))
    }

    @Test
    @DisplayName("event findAll events contains title 로직 검증")
    fun findAllEventsContainsTitle() {
        val eventList: List<Event> =
            listOf(event, Event("title 2", LocalDateTime.of(2022, 1, 7, 11, 19),
                LocalDateTime.of(2022, 1, 8, 19, 19),
                "uu", "60min", "1000", "100", "none", user))
        eventRepository.saveAll(eventList)

        val response: ResultActions = mockMvc.perform(get("/event/title"))

        response.andExpect(status().isOk)
            .andDo(print())
            .andExpect(jsonPath("$['data'].size()", `is`(eventList.size)))
    }
}