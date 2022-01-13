package com.example.dev_cal_kotlin_jpa.integration

import com.example.dev_cal_kotlin_jpa.domain.Event
import com.example.dev_cal_kotlin_jpa.domain.Scrap
import com.example.dev_cal_kotlin_jpa.domain.User
import com.example.dev_cal_kotlin_jpa.dto.EventDto
import com.example.dev_cal_kotlin_jpa.dto.ScrapDto
import com.example.dev_cal_kotlin_jpa.dto.UserDto
import com.example.dev_cal_kotlin_jpa.persistence.EventRepository
import com.example.dev_cal_kotlin_jpa.persistence.ScrapRepository
import com.example.dev_cal_kotlin_jpa.persistence.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.hamcrest.CoreMatchers.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal class ScrapControllerITest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var scrapRepository : ScrapRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var eventRepository: EventRepository

    @Autowired
    lateinit var objectMapper: ObjectMapper

    lateinit var user: User
    lateinit var event: Event
    lateinit var scrap: Scrap

    @BeforeEach
    fun setup() {
        scrapRepository.deleteAll()
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
        scrap = Scrap(event, user)
    }

    @Test
    @DisplayName("scarp 로직 검증")
    fun scrap() {
        val userDto = UserDto("seoin", "jnh123@naver.com", "1234@tjdls", "010-1234-1231")
        val eventDto = EventDto(
            1L, "title 1",
            LocalDateTime.of(2022, 1, 7, 11, 19),
            LocalDateTime.of(2022, 1, 8, 19, 19),
            "host 1",
            "60",
            "1000",
            "100",
            "none",
        )
        val scrapDto = ScrapDto(eventDto,userDto)

        val savedEvent = eventRepository.save(event)

        val response: ResultActions = mockMvc.perform(post("/scrap/{email}/{eventId}", scrap.user.email, savedEvent.id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(scrapDto)))

        response.andDo(print())
            .andExpect(status().isOk)
    }

    @Test
    @DisplayName("delete scrap 로직 검증")
    fun deleteScrap() {
        val userDto = UserDto("seoin", "jnh123@naver.com", "1234@tjdls", "010-1234-1231")
        val eventDto = EventDto(
            1L, "title 1",
            LocalDateTime.of(2022, 1, 7, 11, 19),
            LocalDateTime.of(2022, 1, 8, 19, 19),
            "host 1",
            "60",
            "1000",
            "100",
            "none",
        )
        val scrapDto = ScrapDto(eventDto,userDto)

        val savedEvent = eventRepository.save(event)
        scrapRepository.save(scrap)
        val response: ResultActions = mockMvc.perform(delete("/scrap/{email}/{eventId}", scrap.user.email, savedEvent.id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(scrapDto)))

        response.andDo(print())
            .andExpect(status().isOk)
    }

    @Test
    @DisplayName("findAll 로직 검증")
    fun findAll() {
        eventRepository.save(event)
        val scrapList = listOf(scrap, Scrap(event, user))
        scrapRepository.saveAll(scrapList)
        val response: ResultActions = mockMvc.perform(get("/scrap"))

        response.andExpect(status().isOk)
            .andDo(print())
            .andExpect(jsonPath("$['data'].size()", `is`(scrapList.size)))
    }

    @Test
    @DisplayName("findAll User Scraps 로직 검증 ")
    fun findAllUserScraps() {
        eventRepository.save(event)
        val savedScrap = scrapRepository.save(scrap)
        val response: ResultActions = mockMvc.perform(get("/scrap/{email}", savedScrap.user.email))

        response.andExpect(status().isOk)
            .andDo(print())
            .andExpect(jsonPath("$['data'][0]['event']['title']", `is`(savedScrap.event.title)))
            .andExpect(jsonPath("$['data'][0]['user']['email']", `is`(savedScrap.user.email)))
    }
}