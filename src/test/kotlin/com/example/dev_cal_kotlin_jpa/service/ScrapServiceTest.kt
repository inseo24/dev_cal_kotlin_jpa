package com.example.dev_cal_kotlin_jpa.service

import com.example.dev_cal_kotlin_jpa.domain.Event
import com.example.dev_cal_kotlin_jpa.domain.Scrap
import com.example.dev_cal_kotlin_jpa.domain.User
import com.example.dev_cal_kotlin_jpa.dto.EventDto
import com.example.dev_cal_kotlin_jpa.dto.ScrapDto
import com.example.dev_cal_kotlin_jpa.dto.UserDto
import com.example.dev_cal_kotlin_jpa.persistence.EventRepository
import com.example.dev_cal_kotlin_jpa.persistence.ScrapRepository
import com.example.dev_cal_kotlin_jpa.persistence.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.modelmapper.ModelMapper
import java.time.LocalDateTime
import org.assertj.core.api.Assertions.assertThat
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import java.util.*

@ExtendWith(MockitoExtension::class)
open class ScrapServiceTest {
    @Mock
    lateinit var eventRepository: EventRepository

    @Mock
    lateinit var modelMapper: ModelMapper

    @Mock
    lateinit var userRepository: UserRepository

    @Mock
    lateinit var scrapRepository: ScrapRepository

    @InjectMocks
    lateinit var scrapService: ScrapService


    lateinit var user: User
    lateinit var userDto: UserDto
    lateinit var event: Event
    lateinit var eventDto: EventDto
    lateinit var scrap: Scrap
    lateinit var scrapDto: ScrapDto

    @BeforeEach
    fun setup() {
        user = User("seoin", "jnh123@naver.com", "1234@tjdls", "010-1234-1231")
        userDto = UserDto("seoin", "jnh123@naver.com", "1234@tjdls", "010-1234-1231")
        event = Event("title 1",
            LocalDateTime.of(2022, 1, 7, 11, 19),
            LocalDateTime.of(2022, 1, 8, 19, 19),
            "host 1",
            "60",
            "1000",
            "100",
            "none",
            user)
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
        scrap = Scrap(event, user)
        scrapDto = ScrapDto(eventDto,userDto)
    }

    @Test
    @DisplayName("scrap 로직을 검증한다")
    fun scrapTest() {
        `when`(userRepository.findByEmail(user.email)).thenReturn(user)
        `when`(eventRepository.findById(event.id)).thenReturn(Optional.of(event))
        `when`(scrapRepository.save(scrap)).thenReturn(scrap)

        val response = scrapService.scrap(user.email,event.id)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    }


    @Test
    @DisplayName("delete scrap 로직을 검증한다")
    fun deleteScrapTest() {
        `when`(userRepository.findByEmail(user.email)).thenReturn(user)
        `when`(eventRepository.findById(event.id)).thenReturn(Optional.of(event))
        doNothing().`when`(scrapRepository).deleteScrapByEventIdAndUserId(event.id, user.id)

        scrapService.deleteScrap(user.email, event.id)

        verify(scrapRepository, times(1)).deleteScrapByEventIdAndUserId(event.id, user.id)
    }


    @Test
    @DisplayName("findAll scrap 로직을 검증한다")
    fun findAllScrapTest() {
        val scrapList = listOf(Scrap(event, user), Scrap(event, user))
        `when`(modelMapper.map(scrap, ScrapDto::class.java)).thenReturn(scrapDto)
        `when`(scrapRepository.findAll()).thenReturn(scrapList)

        val response = scrapService.findAll()

        assertThat(response).isNotNull
    }

    @Test
    @DisplayName("findAll user's scrap 로직을 검증한다")
    fun findAllUsersScrapTest() {
        val scrapList = listOf(Scrap(event, user), Scrap(event, user))
        `when`(userRepository.findByEmail(user.email)).thenReturn(user)
        `when`(modelMapper.map(scrap, ScrapDto::class.java)).thenReturn(scrapDto)
        `when`(scrapRepository.findAllByUserId(user.id)).thenReturn(scrapList)

        val response = scrapService.findAllUsersScrap(user.email)

        assertThat(response).isNotNull
    }

}