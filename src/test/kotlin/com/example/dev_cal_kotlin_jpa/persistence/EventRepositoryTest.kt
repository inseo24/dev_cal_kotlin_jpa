package com.example.dev_cal_kotlin_jpa.persistence

import com.example.dev_cal_kotlin_jpa.domain.Event
import com.example.dev_cal_kotlin_jpa.domain.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.LocalDateTime

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EventRepositoryTest {

    @Autowired
    lateinit var eventRepository: EventRepository

    @Autowired
    lateinit var userRepository: UserRepository

    lateinit var user: User
    lateinit var event: Event

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
            "none",
            user)
    }

    @Test
    @DisplayName("event entity 1개를 저장한다")
    fun saveTest() {
        val result = eventRepository.save(event)

        assertThat(result).isNotNull
        assertThat(result.id).isGreaterThan(0)
    }

    @Test
    @DisplayName("event entities 저장 후 findAll 검증")
    fun saveAllAndFindAll() {
        val eventList = listOf(event,
            Event("title 1",
                LocalDateTime.of(2022, 1, 7, 11, 19),
                LocalDateTime.of(2022, 1, 8, 19, 19),
                "host 1",
                "60",
                "1000",
                "100",
                "none",
                user))
        userRepository.save(user)
        eventRepository.saveAll(eventList)
        val result = eventRepository.findAll()

        assertThat(result).isNotNull
        assertThat(result.size).isEqualTo(2)
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
                "none",
                user))
        userRepository.save(user)
        eventRepository.saveAll(eventList)
        val result = eventRepository.findAllEventsByTitleContains("title")

        assertThat(result).isNotEmpty
        assertThat(result.size).isEqualTo(2)
    }
}