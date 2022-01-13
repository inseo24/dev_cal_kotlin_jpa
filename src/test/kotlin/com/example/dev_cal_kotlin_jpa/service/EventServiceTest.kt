package com.example.dev_cal_kotlin_jpa.service

import com.example.dev_cal_kotlin_jpa.domain.Event
import com.example.dev_cal_kotlin_jpa.domain.User
import com.example.dev_cal_kotlin_jpa.dto.EventDto
import com.example.dev_cal_kotlin_jpa.persistence.EventRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.modelmapper.ModelMapper
import java.time.LocalDateTime
import org.assertj.core.api.Assertions.assertThat

@ExtendWith(MockitoExtension::class)
open class EventServiceTest {

    @Mock
    lateinit var eventRepository: EventRepository

    @Mock
    lateinit var modelMapper: ModelMapper

    @InjectMocks
    lateinit var eventService: EventService

    lateinit var user: User
    lateinit var event: Event
    lateinit var eventDto: EventDto

    @BeforeEach
    fun setup() {
        user = User("seoin", "jnh123@naver.com", "1234@tjdls", "010-1234-1231")
        event = Event("title 1",
            LocalDateTime.of(2022, 1, 7, 11, 19),
            LocalDateTime.of(2022, 1, 8, 19, 19),
            "host 1",
            "60",
            "1000",
            "100",
            "none")
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
    }

    @Test
    @DisplayName("event entity 1개를 저장한다")
    fun saveEvent() {
        `when`(modelMapper.map(eventDto, Event::class.java)).thenReturn(event)
        `when`(modelMapper.map(event, EventDto::class.java)).thenReturn(eventDto)
        `when`(eventRepository.save(event)).thenReturn(event)

        val response = eventService.create(eventDto)

        assertThat(response).isNotNull
    }

    @Test
    @DisplayName("findAll 검증")
    fun findAllEvents() {
        val eventList = listOf(event,
            Event("title 1",
                LocalDateTime.of(2022, 1, 7, 11, 19),
                LocalDateTime.of(2022, 1, 8, 19, 19),
                "host 1",
                "60",
                "1000",
                "100",
                "none"))
        `when`(modelMapper.map(event, EventDto::class.java)).thenReturn(eventDto)
        `when`(eventRepository.findAll()).thenReturn(eventList)

        val response = eventService.findAll()

        assertThat(response).isNotNull
    }

    @Test
    @DisplayName("특정 title 을 포함하는 event entities 를 모두 찾기")
    fun findAllEventsContainsTitle() {
        val eventList = listOf(event,
            Event("title 1",
                LocalDateTime.of(2022, 1, 7, 11, 19),
                LocalDateTime.of(2022, 1, 8, 19, 19),
                "host 1",
                "60",
                "1000",
                "100",
                "none"))
        `when`(modelMapper.map(event, EventDto::class.java)).thenReturn(eventDto)
        `when`(eventRepository.findAllEventsByTitleContains(event.title)).thenReturn(eventList)
        eventRepository.saveAll(eventList)

        val response = eventService.findAllEventsContainsTitle(event.title)

        assertThat(response).isNotNull
    }
}